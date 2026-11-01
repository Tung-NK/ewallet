package com.example.ewallet.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionResponse {
    private Long id;
    private String transactionCode;
    private Long fromWalletId;
    private Long toWalletId;
    private BigDecimal amount;
    private String type;
    private String status;
    private String note;
    private BigDecimal balanceAfterFrom;
    private BigDecimal balanceAfterTo;
    private Instant createdAt;

    public TransactionResponse() {
    }

    public TransactionResponse(Long id, String transactionCode,
                               Long fromWalletId, Long toWalletId,
                               BigDecimal amount, String type,
                               String status, String note, BigDecimal balanceAfterFrom,
                               BigDecimal balanceAfterTo, Instant createdAt) {
        this.id = id;
        this.transactionCode = transactionCode;
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.note = note;
        this.balanceAfterFrom = balanceAfterFrom;
        this.balanceAfterTo = balanceAfterTo;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Long getFromWalletId() {
        return fromWalletId;
    }

    public void setFromWalletId(Long fromWalletId) {
        this.fromWalletId = fromWalletId;
    }

    public Long getToWalletId() {
        return toWalletId;
    }

    public void setToWalletId(Long toWalletId) {
        this.toWalletId = toWalletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getBalanceAfterFrom() {
        return balanceAfterFrom;
    }

    public void setBalanceAfterFrom(BigDecimal balanceAfterFrom) {
        this.balanceAfterFrom = balanceAfterFrom;
    }

    public BigDecimal getBalanceAfterTo() {
        return balanceAfterTo;
    }

    public void setBalanceAfterTo(BigDecimal balanceAfterTo) {
        this.balanceAfterTo = balanceAfterTo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
