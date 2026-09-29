/**
 * Package chứa các lớp liên quan đến JWT và Spring Security.
 *
 * <p>Bao gồm:
 * <ul>
 *   <li>{@code JwtTokenProvider} — sinh, verify JWT
 *   <li>{@code JwtAuthenticationFilter} — filter chặn request, đọc JWT, nạp SecurityContext
 *   <li>{@code CustomUserDetailsService} — load user từ DB cho Spring Security
 *   <li>{@code SecurityUtils} — utility lấy userId hiện tại từ SecurityContext (chống IDOR)
 * </ul>
 *
 * @since 1.0
 */
package com.example.ewallet.security;