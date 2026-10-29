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
            "Email hoặc số điện thoại đã được đăng ký"),

    AUTH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,
            "Token đã hết hạn"),

    AUTH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED,
            "Token không hợp lệ"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "Không tìm thấy người dùng"),

    PIN_ALREADY_SET(HttpStatus.CONFLICT,
            "Bạn đã có mã PIN, vui lòng đổi mã PIN"),

    PIN_NOT_SET(HttpStatus.UNPROCESSABLE_ENTITY,
            "Bạn chưa đặt mã PIN, vui lòng đặt mã PIN"),

    INVALID_PIN(HttpStatus.UNAUTHORIZED,
            "Mã PIN không đúng"),

    WALLET_NOT_FOUND(HttpStatus.NOT_FOUND,
            "Không tìm thấy ví"),

    WALLET_INACTIVE(HttpStatus.UNPROCESSABLE_ENTITY,
            "Ví đã bị khóa hoặc vô hiệu hóa"),

    TOPUP_LIMIT_EXCEEDED(HttpStatus.UNPROCESSABLE_ENTITY,
            "Số tiền nạp vượt quá hạn mức cho phép"),

    WALLET_INSUFFICIENT_BALANCE(HttpStatus.UNPROCESSABLE_ENTITY,
            "Số dư không đủ để thực hiện giao dịch"),

    TRANSFER_LIMIT_EXCEEDED(HttpStatus.UNPROCESSABLE_ENTITY,
            "Số tiền chuyển vượt quá hạn mức cho phép"),

    TRANSFER_SAME_WALLET(HttpStatus.UNPROCESSABLE_ENTITY,
            "Không thể chuyển tiền cho chính mình");

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
