package com.example.ewallet.service.impl;

import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.entity.User;
import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import com.example.ewallet.form.UpdateProfileForm;
import com.example.ewallet.repository.UserRepository;
import com.example.ewallet.security.SecurityUtils;
import com.example.ewallet.service.UserService;
import com.example.ewallet.util.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
