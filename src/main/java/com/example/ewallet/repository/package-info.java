/**
 * Package chứa Spring Data JPA Repository.
 *
 * <p>Trách nhiệm:
 * <ul>
 *   <li>Giao tiếp với database
 *   <li>Định nghĩa các phương thức truy vấn (findByEmail, existsByPhone, ...)
 *   <li>Có thể dùng {@code @Query} cho truy vấn phức tạp
 * </ul>
 *
 * <p><b>KHÔNG</b> chứa logic nghiệp vụ — chỉ truy vấn dữ liệu thuần túy.
 *
 * @since 1.0
 */
package com.example.ewallet.repository;