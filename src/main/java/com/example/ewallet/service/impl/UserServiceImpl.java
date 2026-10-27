package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.entity.User;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.form.ChangePinForm;
import com.example.ewallet.form.SetPinForm;
import com.example.ewallet.form.UpdateProfileForm;
import com.example.ewallet.repository.UserRepository;
import com.example.ewallet.security.SecurityUtils;
import com.example.ewallet.service.UserService;
import com.example.ewallet.util.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentProfile() {
        Long userId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateCurrentProfile(UpdateProfileForm form) {
        Long userId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.setFullName(form.getFullName().trim());
        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void setPin(SetPinForm form) {
        Long userId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getTransactionPin() != null) {
            throw new BusinessException(ErrorCode.PIN_ALREADY_SET);
        }

        if (!form.getPin().equals(form.getConfirmPin())) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR,
                    "Mã PIN và xác nhận mã PIN không khớp");
        }

        user.setTransactionPin(passwordEncoder.encode(form.getPin()));
        userRepository.save(user);

    }

    @Override
    @Transactional
    public void changePin(ChangePinForm form) {
        Long userId = SecurityUtils.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getTransactionPin() == null) {
            throw new BusinessException(ErrorCode.PIN_NOT_SET);
        }

        if (!passwordEncoder.matches(form.getOldPin(), user.getTransactionPin())) {
            throw new BusinessException(ErrorCode.INVALID_PIN, "Mã PIN cũ không đúng");
        }

        if (!form.getNewPin().equals(form.getConfirmNewPin())) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR,
                    "Mã PIN mới và xác nhận mã PIN mới không khớp");
        }

        if (form.getNewPin().equals(form.getOldPin())) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR,
                    "Mã PIN mới phải khác mã PIN cũ");
        }

        user.setTransactionPin(passwordEncoder.encode(form.getNewPin()));
        userRepository.save(user);

    }
}
