/**
 * Package chứa Service <b>implementation</b>.
 *
 * <p>Trách nhiệm:
 * <ul>
 *   <li>Xử lý nghiệp vụ thực tế
 *   <li>Kiểm tra điều kiện nghiệp vụ (số dư đủ, PIN đúng, ví ACTIVE, ...)
 *   <li>Mở {@code @Transactional} cho nghiệp vụ tài chính
 *   <li>Kiểm tra quyền sở hữu tài nguyên (ownership check) — chống IDOR
 *   <li>Gọi Repository
 * </ul>
 *
 * <p>Đây là <b>tầng quan trọng nhất</b> — nơi chứa toàn bộ logic nghiệp vụ.
 *
 * @since 1.0
 */
package com.example.ewallet.service.impl;