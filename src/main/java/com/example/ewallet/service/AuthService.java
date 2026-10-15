package com.example.ewallet.service;

import com.example.ewallet.dto.response.LoginResponse;
import com.example.ewallet.dto.response.RefreshTokenResponse;
import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.form.LoginForm;
import com.example.ewallet.form.RefreshTokenForm;
import com.example.ewallet.form.RegisterForm;

public interface AuthService {
    LoginResponse login(LoginForm form);

    UserResponse register(RegisterForm form);

    RefreshTokenResponse refreshToken(RefreshTokenForm form);
}
