package com.example.ewallet.controller;

import com.example.ewallet.dto.response.ApiResponse;
import com.example.ewallet.dto.response.LoginResponse;
import com.example.ewallet.form.LoginForm;
import com.example.ewallet.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginForm form) {

        LoginResponse data = authService.login(form);
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", data));
    }
}
