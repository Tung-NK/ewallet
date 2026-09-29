/**
 * Package chứa DTO <b>dùng nội bộ</b> giữa các Service với nhau.
 *
 * <p>Đặc điểm:
 * <ul>
 *   <li>KHÔNG nhận trực tiếp từ HTTP request body (dùng {@code form/} cho mục đích đó)
 *   <li>Dùng khi Service A cần chuyển dữ liệu đã chuẩn hóa sang Service B
 *   <li>Dùng khi cần filter/criteria truyền xuống Repository
 * </ul>
 *
 * @since 1.0
 */
package com.example.ewallet.dto.request;