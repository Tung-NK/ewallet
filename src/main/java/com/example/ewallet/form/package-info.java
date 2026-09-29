/**
 * Package chứa các lớp <b>nhận input trực tiếp từ HTTP request body</b>.
 *
 * <p>Đặc điểm:
 * <ul>
 *   <li>Chứa annotation Bean Validation ({@code @NotNull}, {@code @Size}, {@code @Pattern}, {@code @Email}, ...)
 *   <li>Được dùng cùng {@code @Valid} ở Controller
 *   <li>Đây là lớp <b>đầu tiên</b> tiếp nhận dữ liệu client — chặn dữ liệu bẩn ngay từ cửa ngõ
 * </ul>
 *
 * <p>Form dự kiến: {@code RegisterForm}, {@code LoginForm}, {@code RefreshTokenForm},
 * {@code UpdateProfileForm}, {@code SetPinForm}, {@code TopupForm}, {@code TransferForm}.
 *
 * @since 1.0
 */
package com.example.ewallet.form;