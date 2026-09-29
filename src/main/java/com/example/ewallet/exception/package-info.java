/**
 * Package chứa Exception tùy chỉnh và GlobalExceptionHandler.
 *
 * <p>Bao gồm:
 * <ul>
 *   <li>{@code GlobalExceptionHandler} — {@code @RestControllerAdvice} xử lý toàn bộ exception
 *   <li>{@code BusinessException} — exception nghiệp vụ có errorCode
 *   <li>Các exception con kế thừa BusinessException (UserNotFoundException, ...)
 * </ul>
 *
 * <p>Nguyên tắc: KHÔNG để lộ stack trace, SQL, class name ra response.
 *
 * @since 1.0
 */
package com.example.ewallet.exception;