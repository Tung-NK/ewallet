package com.example.ewallet.service;

import com.example.ewallet.dto.response.TopupResponse;
import com.example.ewallet.dto.response.WalletBalanceResponse;
import com.example.ewallet.form.TopupForm;

public interface WalletService {
    WalletBalanceResponse getBalance();

    TopupResponse topup(TopupForm form);
}
