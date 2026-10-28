package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.TopupResponse;
import com.example.ewallet.dto.response.WalletBalanceResponse;
import com.example.ewallet.entity.*;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.form.TopupForm;
import com.example.ewallet.repository.TransactionRepository;
import com.example.ewallet.repository.WalletRepository;
import com.example.ewallet.security.SecurityUtils;
import com.example.ewallet.service.WalletService;
import com.example.ewallet.util.TransactionCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {
    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Value("${wallet.topup.max-per-transaction}")
    private BigDecimal maxTopupAmount;

    public WalletServiceImpl(WalletRepository walletRepository,
                             TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public WalletBalanceResponse getBalance() {
        Long userId = SecurityUtils.getCurrentUserId();

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));

        return new WalletBalanceResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getCurrency(),
                wallet.getStatus().name());
    }

    @Override
    @Transactional
    public TopupResponse topup(TopupForm form) {
        Long userId = SecurityUtils.getCurrentUserId();

        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));

        if (wallet.getStatus() == WalletStatus.LOCKED) {
            throw new BusinessException(ErrorCode.WALLET_INACTIVE);
        }

        BigDecimal amount = form.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR, "Số tiền phải lớn hơn 0");
        }

        if (amount.compareTo(maxTopupAmount)>0){
            throw new BusinessException(ErrorCode.TOPUP_LIMIT_EXCEEDED,
                    "Số tiền nạp tối đa mỗi lần là " + maxTopupAmount + " VND");
        }

        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        String note = form.getNote() != null ? form.getNote().trim() : null;

        Transaction transaction = new Transaction(
                TransactionCodeGenerator.generate(),
                null,
                wallet.getId(),
                amount,
                TransactionType.TOPUP);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setNote(note);
        transaction.setBalanceAfterFrom(null);
        transaction.setBalanceAfterTo(newBalance);

        transactionRepository.save(transaction);

        return new TopupResponse(transaction.getId(), amount, newBalance);
    }
}
