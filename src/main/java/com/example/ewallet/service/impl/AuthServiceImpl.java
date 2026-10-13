package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.LoginResponse;
import com.example.ewallet.entity.User;
import com.example.ewallet.entity.UserStatus;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.form.LoginForm;
import com.example.ewallet.repository.UserRepository;
import com.example.ewallet.security.JwtTokenProvider;
import com.example.ewallet.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String TOKEN_TYPE_BEARER = "Bearer";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginResponse login(LoginForm form) {
        String identifier = form.getEmailOrPhone().trim();

        User user = userRepository.findByEmailOrPhone(identifier, identifier).orElseThrow(
                () -> new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(form.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        if (user.getStatus() == UserStatus.LOCKED) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "Tài khoản đã bị khoá");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(), user.getEmail());

        log.info("User logged in: id={}", user.getId());

        return new LoginResponse(
                accessToken, refreshToken, TOKEN_TYPE_BEARER, jwtTokenProvider.getAccessTokenExpirationSeconds()
        );
    }
}
