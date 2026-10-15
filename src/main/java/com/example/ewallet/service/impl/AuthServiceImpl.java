package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.LoginResponse;
import com.example.ewallet.dto.response.RefreshTokenResponse;
import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.entity.User;
import com.example.ewallet.entity.UserStatus;
import com.example.ewallet.entity.Wallet;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.form.LoginForm;
import com.example.ewallet.form.RefreshTokenForm;
import com.example.ewallet.form.RegisterForm;
import com.example.ewallet.repository.UserRepository;
import com.example.ewallet.repository.WalletRepository;
import com.example.ewallet.security.JwtTokenProvider;
import com.example.ewallet.service.AuthService;
import com.example.ewallet.util.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String TOKEN_TYPE_BEARER = "Bearer";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final WalletRepository walletRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           WalletRepository walletRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
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

        return new LoginResponse(
                accessToken, refreshToken, TOKEN_TYPE_BEARER, jwtTokenProvider.getAccessTokenExpirationSeconds()
        );
    }

    @Override
    @Transactional
    public UserResponse register(RegisterForm form) {
        String email = form.getEmail().trim().toLowerCase();
        String phone = form.getPhone().trim();
        String fullName = form.getFullName().trim();

        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS, "Email đã được đăng ký");
        }

        if (userRepository.existsByPhone(phone)) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS, "Số điện thoại đã được đăng ký");
        }

        String passwordHash = passwordEncoder.encode(form.getPassword());

        User user = new User(fullName, email, phone, passwordHash);
        userRepository.save(user);

        Wallet wallet = new Wallet(user.getId());
        walletRepository.save(wallet);

        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshTokenResponse refreshToken(RefreshTokenForm form) {
        Long userId = jwtTokenProvider.parseRefreshTokenAndGetUserId(form.getRefreshToken());

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.AUTH_TOKEN_INVALID));

        if (user.getStatus() == UserStatus.LOCKED) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "Tài khoản đã bị khóa");
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), user.getRole().name());

        return new RefreshTokenResponse(
                newAccessToken,
                TOKEN_TYPE_BEARER,
                jwtTokenProvider.getAccessTokenExpirationSeconds()
        );
    }
}
