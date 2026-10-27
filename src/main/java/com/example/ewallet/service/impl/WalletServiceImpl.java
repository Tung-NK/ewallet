package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.WalletBalanceResponse;
import com.example.ewallet.entity.Wallet;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.repository.WalletRepository;
import com.example.ewallet.security.SecurityUtils;
import com.example.ewallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
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
}
