package com.example.ewallet.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_code", nullable = false, unique = true, length = 30)
    private String transactionCode;

    @Column(name = "from_wallet_id")
    private Long fromWalletId;

    @Column(name = "to_wallet_id")
    private Long toWalletId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransactionStatus status;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "balance_after_from", precision = 19, scale = 2)
    private BigDecimal balanceAfterFrom;

    @Column(name = "balance_after_to", precision = 19, scale = 2)
    private BigDecimal balanceAfterTo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructors

    public Transaction() {
    }

    public Transaction(String transactionCode, Long fromWalletId, Long toWalletId, BigDecimal amount, TransactionType type) {
        this.transactionCode = transactionCode;
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.amount = amount;
        this.type = type;
    }

    // Lifecycle callbacks

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
        if (this.status == null) {
            this.status = TransactionStatus.PENDING;
        }
    }

    // Getters / Setters

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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // equals / hashCode / toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction other = (Transaction) o;
        return id != null && id.equals(other.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionCode='" + transactionCode + '\'' +
                ", fromWalletId=" + fromWalletId +
                ", toWalletId=" + toWalletId +
                ", amount=" + amount +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
