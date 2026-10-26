package com.example.ewallet.security;

import com.example.ewallet.exception.BusinessException;
import com.example.ewallet.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
/**
 * Lấy thông tin user hiện tại từ {@link SecurityContextHolder}.
 *
 * <p>Đây là <b>cách duy nhất</b> được phép xác định "ai đang thao tác" — chống IDOR.
 */
public class SecurityUtils {
    private SecurityUtils() {
    }

    /**
     * Lấy userId của user đang đăng nhập.
     *
     * <p>Principal được set bởi {@link JwtAuthenticationFilter} dưới dạng {@link Long}.
     *
     * @return userId
     * @throws BusinessException {@code AUTH_TOKEN_INVALID} nếu chưa authenticated
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !(authentication.getPrincipal() instanceof Long userId)) {
            throw new BusinessException(ErrorCode.AUTH_TOKEN_INVALID);
        }

        return userId;
    }
}
