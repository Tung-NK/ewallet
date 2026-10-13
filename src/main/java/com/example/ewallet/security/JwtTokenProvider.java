package com.example.ewallet.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    private static final int MIN_SECRET_BYTES = 32;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-minutes}")
    private long accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-days}")
    private long refreshTokenExpirationDays;

    private SecretKey signingKey;

    @PostConstruct
    void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < MIN_SECRET_BYTES) {
            throw new IllegalStateException(
                    "JWT secret must be at least " + MIN_SECRET_BYTES + " bytes (256-bit). "
                            + "Current length: " + keyBytes.length);
        }
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Long userId, String email, String role) {
        Instant now = Instant.now();
        Instant expiry = now.plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(CLAIM_EMAIL, email)
                .claim(CLAIM_ROLE, role)
                .claim(CLAIM_TYPE, TYPE_ACCESS)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(signingKey)
                .compact();
    }

    public String generateRefreshToken(Long userId, String email) {
        Instant now = Instant.now();
        Instant expiry = now.plus(refreshTokenExpirationDays, ChronoUnit.DAYS);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(CLAIM_EMAIL, email)
                .claim(CLAIM_TYPE, TYPE_REFRESH)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(signingKey)
                .compact();
    }

    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationMinutes * 60;
    }
}
