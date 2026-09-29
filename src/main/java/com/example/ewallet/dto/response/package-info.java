/**
 * Package chứa DTO <b>trả ra client</b>.
 *
 * <p>Quy tắc bắt buộc:
 * <ul>
 *   <li>KHÔNG chứa {@code passwordHash}, {@code transactionPin} hay bất kỳ trường nội bộ
 *   <li>Luôn được bọc trong {@code ApiResponse<T>} hoặc {@code PageResponse<T>}
 *   <li>Timestamp format ISO 8601 UTC
 * </ul>
 *
 * @since 1.0
 */
package com.example.ewallet.dto.response;