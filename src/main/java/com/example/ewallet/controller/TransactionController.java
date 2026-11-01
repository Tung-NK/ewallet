package com.example.ewallet.controller;

import com.example.ewallet.dto.response.ApiResponse;
import com.example.ewallet.dto.response.PageResponse;
import com.example.ewallet.dto.response.TransactionResponse;
import com.example.ewallet.entity.TransactionStatus;
import com.example.ewallet.entity.TransactionType;
import com.example.ewallet.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<TransactionResponse>>> getHistory(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) String sort) {

        PageResponse<TransactionResponse> data = transactionService.getHistory(
                page, size, type, status, fromDate, toDate, sort);

        return ResponseEntity.ok(
                ApiResponse.success("Lấy lịch sử giao dịch thành công", data));
    }
}