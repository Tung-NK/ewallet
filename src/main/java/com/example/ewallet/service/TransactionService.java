package com.example.ewallet.service;

import com.example.ewallet.dto.response.PageResponse;
import com.example.ewallet.dto.response.TransactionResponse;
import com.example.ewallet.entity.TransactionStatus;
import com.example.ewallet.entity.TransactionType;

import java.time.LocalDate;

public interface TransactionService {
    PageResponse<TransactionResponse> getHistory(Integer page,
                                                 Integer size,
                                                 TransactionType type,
                                                 TransactionStatus status,
                                                 LocalDate fromDate,
                                                 LocalDate toDate,
                                                 String sort);
}
