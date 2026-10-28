package com.example.ewallet.dto.response;

import java.math.BigDecimal;

public class TopupResponse {
    private Long transactionId;
    private BigDecimal amount;
    private BigDecimal balanceAfter;

    public TopupResponse() {
    }

    public TopupResponse(Long transactionId, BigDecimal amount, BigDecimal balanceAfter) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}
