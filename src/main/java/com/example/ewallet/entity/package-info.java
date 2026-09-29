/**
 * Package chứa các JPA Entity (ánh xạ bảng DB) và Enum nghiệp vụ.
 *
 * <p>Entity dự kiến:
 * <ul>
 *   <li>{@code User} — bảng users
 *   <li>{@code Wallet} — bảng wallets
 *   <li>{@code Transaction} — bảng transactions
 * </ul>
 *
 * <p>Enum dự kiến:
 * <ul>
 *   <li>{@code Role} — USER, ADMIN
 *   <li>{@code UserStatus} — ACTIVE, LOCKED, PENDING_VERIFICATION
 *   <li>{@code WalletStatus} — ACTIVE, LOCKED
 *   <li>{@code TransactionType} — TOPUP, TRANSFER, WITHDRAW
 *   <li>{@code TransactionStatus} — PENDING, SUCCESS, FAILED
 * </ul>
 *
 * <p><b>KHÔNG</b> trả Entity trực tiếp ra API — luôn map sang Response DTO.
 *
 * @since 1.0
 */
package com.example.ewallet.entity;