package com.example.ewallet.dto.response;

import java.math.BigDecimal;

public class WalletBalanceResponse {
    private Long walletId;
    private BigDecimal balance;
    private String currency;
    private String status;

    public WalletBalanceResponse() {
    }

    public WalletBalanceResponse(Long walletId, BigDecimal balance, String currency, String status) {
        this.walletId = walletId;
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
