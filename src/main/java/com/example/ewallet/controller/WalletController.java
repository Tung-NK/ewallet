package com.example.ewallet.controller;

import com.example.ewallet.dto.response.ApiResponse;
import com.example.ewallet.dto.response.WalletBalanceResponse;
import com.example.ewallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(value = "/balance")
    public ResponseEntity<ApiResponse<WalletBalanceResponse>> getBalance() {
        WalletBalanceResponse data = walletService.getBalance();
        return ResponseEntity.ok(ApiResponse.success("Lấy số dư thành công", data));
    }

}
