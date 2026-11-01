package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.PageResponse;
import com.example.ewallet.dto.response.TransactionResponse;
import com.example.ewallet.entity.Transaction;
import com.example.ewallet.entity.TransactionStatus;
import com.example.ewallet.entity.TransactionType;
import com.example.ewallet.entity.Wallet;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.repository.TransactionRepository;
import com.example.ewallet.repository.WalletRepository;
import com.example.ewallet.security.SecurityUtils;
import com.example.ewallet.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;
    private static final String DEFAULT_SORT_FIELD = "createdAt";
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    private static final String[] ALLOWED_SORT_FIELDS = {"createdAt", "amount"};

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TransactionResponse> getHistory(Integer page, Integer size,
                                                        TransactionType type, TransactionStatus status,
                                                        LocalDate fromDate, LocalDate toDate, String sort) {
        Long userId = SecurityUtils.getCurrentUserId();

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WALLET_NOT_FOUND));

        Pageable pageable = buildPageable(page, size, sort);

        LocalDateTime fromDateTime = fromDate != null
                ? fromDate.atStartOfDay()
                : null;
        LocalDateTime toDateTime = toDate != null
                ? toDate.atTime(23, 59, 59, 999_999_999)
                : null;

        Page<Transaction> pageResult = transactionRepository.findHistory(
                wallet.getId(), type, status, fromDateTime, toDateTime, pageable);

        return new PageResponse<>(
                pageResult.getContent().stream()
                        .map(this::toResponse)
                        .toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.isLast());
    }

    private Pageable buildPageable(Integer page, Integer size, String sort) {
        int safePage = (page == null || page < 0) ? DEFAULT_PAGE : page;
        int safeSize = (size == null || size < 1)
                ? DEFAULT_SIZE
                : Math.min(size, MAX_SIZE);

        return PageRequest.of(safePage, safeSize, parseSort(sort));
    }

    /**
     * Parse chuỗi sort. Chỉ chấp nhận field trong whitelist
     * ({@code createdAt}, {@code amount}); field khác → default.
     * Direction mặc định là {@code desc}.
     */
    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(DEFAULT_SORT_DIRECTION, DEFAULT_SORT_FIELD);
        }

        String[] parts = sort.split(",");
        String field = parts[0].trim();
        String direction = parts.length > 1 ? parts[1].trim() : "desc";

        if (!isAllowedSortField(field)) {
            return Sort.by(DEFAULT_SORT_DIRECTION, DEFAULT_SORT_FIELD);
        }

        Sort.Direction dir = "asc".equalsIgnoreCase(direction)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return Sort.by(dir, field);
    }

    private boolean isAllowedSortField(String field) {
        for (String allowed : ALLOWED_SORT_FIELDS) {
            if (allowed.equals(field)) {
                return true;
            }
        }
        return false;
    }

    private TransactionResponse toResponse(Transaction tx) {
        return new TransactionResponse(
                tx.getId(),
                tx.getTransactionCode(),
                tx.getFromWalletId(),
                tx.getToWalletId(),
                tx.getAmount(),
                tx.getType().name(),
                tx.getStatus().name(),
                tx.getNote(),
                tx.getBalanceAfterFrom(),
                tx.getBalanceAfterTo(),
                tx.getCreatedAt()
                        .toInstant(ZoneOffset.UTC)
                        .truncatedTo(ChronoUnit.SECONDS));
    }
}
