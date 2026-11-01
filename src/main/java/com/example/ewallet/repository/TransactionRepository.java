package com.example.ewallet.repository;

import com.example.ewallet.entity.Transaction;
import com.example.ewallet.entity.TransactionStatus;
import com.example.ewallet.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionCode(String transactionCode);

    @Query("""
            SELECT t FROM Transaction t
            WHERE (t.fromWalletId = :walletId OR t.toWalletId = :walletId)
              AND (:type      IS NULL OR t.type = :type)
              AND (:status    IS NULL OR t.status = :status)
              AND (:fromDate  IS NULL OR t.createdAt >= :fromDate)
              AND (:toDate    IS NULL OR t.createdAt <= :toDate)
            """)
    Page<Transaction> findHistory(
            @Param("walletId") Long walletId,
            @Param("type")     TransactionType type,
            @Param("status")   TransactionStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate")   LocalDateTime toDate,
            Pageable pageable);


    @Query("""
            SELECT t FROM Transaction t
            WHERE t.id = :id
              AND (t.fromWalletId = :walletId OR t.toWalletId = :walletId)
            """)
    Optional<Transaction> findByIdAndWalletId(
            @Param("id")       Long id,
            @Param("walletId") Long walletId);
}