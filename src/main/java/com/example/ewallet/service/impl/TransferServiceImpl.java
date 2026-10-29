package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.TransferResponse;
import com.example.ewallet.entity.*;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.form.TransferForm;
import com.example.ewallet.repository.TransactionRepository;
import com.example.ewallet.repository.UserRepository;
import com.example.ewallet.repository.WalletRepository;
import com.example.ewallet.security.SecurityUtils;
import com.example.ewallet.service.TransferService;
import com.example.ewallet.util.TransactionCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TransferServiceImpl implements TransferService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${wallet.transfer.max-per-transaction}")
    private BigDecimal maxTransferAmount;

    public TransferServiceImpl(UserRepository userRepository,
                               WalletRepository walletRepository,
                               TransactionRepository transactionRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public TransferResponse transfer(TransferForm form) {
        Long fromUserId = SecurityUtils.getCurrentUserId();

        // ---- 1. Load ví nguồn ----
        Wallet fromWallet = walletRepository.findByUserId(fromUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));

        // ---- 2. Load user nguồn + verify PIN ----
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (fromUser.getTransactionPin() == null) {
            throw new BusinessException(ErrorCode.PIN_NOT_SET);
        }

        if (!passwordEncoder.matches(form.getPin(), fromUser.getTransactionPin())) {
            throw new BusinessException(ErrorCode.INVALID_PIN);
        }

        // ---- 3. Tìm user đích theo email/phone ----
        String recipientIdentifier = form.getToPhoneOrEmail().trim().toLowerCase();
        User toUser = userRepository
                .findByEmailOrPhone(recipientIdentifier, recipientIdentifier)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // ---- 4. Load ví đích ----
        Wallet toWallet = walletRepository.findByUserId(toUser.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));

        // ---- 5. Chặn chuyển cho chính mình ----
        if (fromWallet.getId().equals(toWallet.getId())) {
            throw new BusinessException(ErrorCode.TRANSFER_SAME_WALLET);
        }

        // ---- 6. Check cả 2 ví ACTIVE ----
        if (fromWallet.getStatus() != WalletStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.WALLET_INACTIVE,
                    "Ví nguồn đã bị khóa");
        }
        if (toWallet.getStatus() != WalletStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.WALLET_INACTIVE,
                    "Ví người nhận đã bị khóa");
        }

        // ---- 7. Validate amount ----
        BigDecimal amount = form.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR,
                    "Số tiền phải lớn hơn 0");
        }
        if (amount.compareTo(maxTransferAmount) > 0) {
            throw new BusinessException(ErrorCode.TRANSFER_LIMIT_EXCEEDED,
                    "Số tiền chuyển tối đa mỗi lần là " + maxTransferAmount + " VND");
        }

        // ---- 8. Double-check balance ngay trong transaction ----
        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(ErrorCode.WALLET_INSUFFICIENT_BALANCE);
        }

        // ---- 9. Trừ ví nguồn + cộng ví đích ----
        BigDecimal newFromBalance = fromWallet.getBalance().subtract(amount);
        BigDecimal newToBalance = toWallet.getBalance().add(amount);

        fromWallet.setBalance(newFromBalance);
        toWallet.setBalance(newToBalance);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        // ---- 10. Tạo Transaction ----
        String note = form.getNote() != null ? form.getNote().trim() : null;

        Transaction transaction = new Transaction(
                TransactionCodeGenerator.generate(),
                fromWallet.getId(),
                toWallet.getId(),
                amount,
                TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setNote(note);
        transaction.setBalanceAfterFrom(newFromBalance);
        transaction.setBalanceAfterTo(newToBalance);

        transactionRepository.save(transaction);


        return new TransferResponse(
                transaction.getTransactionCode(),
                amount,
                toUser.getFullName(),
                newFromBalance,
                transaction.getCreatedAt()
                        .toInstant(ZoneOffset.UTC)
                        .truncatedTo(ChronoUnit.SECONDS));
    }
}
