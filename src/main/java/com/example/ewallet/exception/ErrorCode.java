package com.example.ewallet.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST,
            "Dữ liệu đầu vào không hợp lệ"),

    AUTH_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED,
            "Email/số điện thoại hoặc mật khẩu không đúng"),

    FORBIDDEN(HttpStatus.FORBIDDEN,
            "Không có quyền truy cập tài nguyên"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
            "Lỗi hệ thống"),

    USER_ALREADY_EXISTS(HttpStatus.CONFLICT,
            "Email hoặc số điện thoại đã được đăng ký");

    private final HttpStatus httpStatus;
    private final String defaultMessage;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    ErrorCode(HttpStatus httpStatus, String defaultMessage) {
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }

}
