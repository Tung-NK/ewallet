package com.example.ewallet.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public class TransferResponse {
    private String transactionCode;
    private BigDecimal amount;
    private String toUser;
    private BigDecimal balanceAfter;
    private Instant createdAt;

    public TransferResponse() {
    }

    public TransferResponse(String transactionCode, BigDecimal amount, String toUser, BigDecimal balanceAfter, Instant createdAt) {
        this.transactionCode = transactionCode;
        this.amount = amount;
        this.toUser = toUser;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
