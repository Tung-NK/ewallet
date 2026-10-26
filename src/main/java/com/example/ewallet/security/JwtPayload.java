package com.example.ewallet.security;

public record JwtPayload(Long userId, String email, String role) {
}
