package com.example.ewallet.service;

import com.example.ewallet.dto.response.WalletBalanceResponse;

public interface WalletService {
    WalletBalanceResponse getBalance();
}
