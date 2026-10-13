package com.example.ewallet.service;

import com.example.ewallet.dto.response.LoginResponse;
import com.example.ewallet.form.LoginForm;

public interface AuthService {
    LoginResponse login(LoginForm form);
}
