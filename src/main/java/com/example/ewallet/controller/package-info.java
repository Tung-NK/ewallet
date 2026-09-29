/**
 * Package chứa các REST Controller.
 *
 * <p>Trách nhiệm:
 * <ul>
 *   <li>Nhận HTTP request, validate form ({@code @Valid})
 *   <li>Gọi Service xử lý nghiệp vụ
 *   <li>Trả về Response Wrapper thống nhất
 * </ul>
 *
 * <p><b>KHÔNG</b> xử lý nghiệp vụ, <b>KHÔNG</b> truy cập Repository trực tiếp.
 *
 * @since 1.0
 */
package com.example.ewallet.controller;