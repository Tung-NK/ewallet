package com.example.ewallet.exception;

import com.example.ewallet.dto.response.ApiResponse;
import com.example.ewallet.dto.response.FieldErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Bắt tất cả exception ở tầng Controller và map sang response thống nhất.
 * <p>
 * class name ra response cho client.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Bắt lỗi nghiệp vụ — trả về errorCode tương ứng trong exception.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        ErrorCode code = ex.getErrorCode();
        log.warn("Business exception: errorCode={}, message={}", code, ex.getMessage());
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage(), code.name());
        return ResponseEntity.status(code.getHttpStatus()).body(body);
    }

    /**
     * Bắt lỗi validation của @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<FieldErrorResponse> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldErrorResponse)
                .toList();

        log.warn("Validation failed: {} field(s)", fieldErrors.size());

        ApiResponse<Void> body = ApiResponse.error(
                ErrorCode.VALIDATION_ERROR.getDefaultMessage(),
                ErrorCode.VALIDATION_ERROR.name(),
                fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Bắt lỗi vi phạm ràng buộc DB (UNIQUE, CHECK, FK).
     *
     * <p>Trong chức năng Register, nguyên nhân duy nhất có thể xảy ra là
     * UNIQUE trùng email/phone (race condition khi 2 request cùng đăng ký
     * cùng 1 email/phone trong tích tắc).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        String cause = ex.getMostSpecificCause().getMessage();
        log.warn("Data integrity violation: {}", cause);

        boolean isUniqueViolation = cause != null
                && (cause.contains("uk_users_email") || cause.contains("uk_users_phone"));

        if (isUniqueViolation) {
            ApiResponse<Void> body = ApiResponse.error(
                    ErrorCode.USER_ALREADY_EXISTS.getDefaultMessage(),
                    ErrorCode.USER_ALREADY_EXISTS.name());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
        }

        ApiResponse<Void> body = ApiResponse.error(
                ErrorCode.INTERNAL_SERVER_ERROR.getDefaultMessage(),
                ErrorCode.INTERNAL_SERVER_ERROR.name());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    /**
     * Catch-all — mọi exception không được bắt ở trên.
     * Log full stack trace ở server, nhưng KHÔNG trả chi tiết cho client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unhandled exception", ex);
        ApiResponse<Void> body = ApiResponse.error(
                ErrorCode.INTERNAL_SERVER_ERROR.getDefaultMessage(),
                ErrorCode.INTERNAL_SERVER_ERROR.name());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private FieldErrorResponse toFieldErrorResponse(FieldError fieldError) {
        return new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage());
    }
}