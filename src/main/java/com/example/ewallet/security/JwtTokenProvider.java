package com.example.ewallet.security;

import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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

    public Long parseRefreshTokenAndGetUserId(String token) { // phân tích refreshToken và lấy id
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String type = claims.get(CLAIM_TYPE, String.class);
            if (!TYPE_REFRESH.equals(type)) {
                throw new BusinessException(ErrorCode.AUTH_TOKEN_INVALID);
            }

            return Long.parseLong(claims.getSubject());
        } catch (ExpiredJwtException ex) {
            throw new BusinessException(ErrorCode.AUTH_TOKEN_EXPIRED);
        } catch (BusinessException ex) {
            throw ex;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new BusinessException(ErrorCode.AUTH_TOKEN_INVALID);
        }
    }

    public JwtPayload parseAccessToken(String token) { // phân tích refreshToken
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String type = claims.get(CLAIM_TYPE, String.class);
            if (!TYPE_ACCESS.equals(type)) {
                throw new BusinessException(ErrorCode.AUTH_TOKEN_INVALID);
            }

            Long userId = Long.parseLong(claims.getSubject());
            String email = claims.get(CLAIM_EMAIL, String.class);
            String role = claims.get(CLAIM_ROLE, String.class);

            return new JwtPayload(userId, email, role);
        } catch (ExpiredJwtException ex) {
            throw new BusinessException(ErrorCode.AUTH_TOKEN_EXPIRED);
        } catch (BusinessException ex) {
            throw ex;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new BusinessException(ErrorCode.AUTH_TOKEN_INVALID);
        }
    }

    public long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationMinutes * 60;
    }
}
