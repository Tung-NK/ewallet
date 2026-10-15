package com.example.ewallet.controller;

import com.example.ewallet.dto.response.ApiResponse;
import com.example.ewallet.dto.response.LoginResponse;
import com.example.ewallet.dto.response.RefreshTokenResponse;
import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.form.LoginForm;
import com.example.ewallet.form.RefreshTokenForm;
import com.example.ewallet.form.RegisterForm;
import com.example.ewallet.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterForm form) {
        UserResponse data = authService.register(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Đăng ký thành công", data));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenForm form) {
        RefreshTokenResponse data = authService.refreshToken(form);
        return ResponseEntity.ok(ApiResponse.success("Làm mới token thành công", data));
    }
}
