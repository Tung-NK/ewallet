package com.example.ewallet.form;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenForm {

    @NotBlank(message = "Refresh token không được để trống")
    private String refreshToken;

    public RefreshTokenForm() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
