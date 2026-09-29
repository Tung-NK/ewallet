/**
 * Package chứa các lớp cấu hình Spring Boot.
 *
 * <p>Bao gồm:
 * <ul>
 *   <li>{@code SecurityConfig} — cấu hình Spring Security, SecurityFilterChain, PasswordEncoder
 *   <li>{@code CorsConfig} — cấu hình CORS
 *   <li>{@code JpaAuditingConfig} — cấu hình auditing createdAt/updatedAt (nếu dùng)
 *   <li>{@code AppProperties} — bind các property nghiệp vụ (wallet.topup.max-per-transaction, ...)
 * </ul>
 *
 * @since 1.0
 */
package com.example.ewallet.config;