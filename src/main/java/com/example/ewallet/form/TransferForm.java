package com.example.ewallet.form;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransferForm {
    @NotBlank(message = "Email hoặc số điện thoại người nhận không được để trống")
    private String toPhoneOrEmail;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.01", inclusive = true, message = "Số tiền phải lớn hơn 0")
    @Digits(integer = 17, fraction = 2, message = "Số tiền chỉ được có tối đa 2 chữ số thập phân")
    private BigDecimal amount;

    @NotBlank(message = "Mã PIN không được để trống")
    @Pattern(regexp = "^\\d{6}$", message = "Mã PIN phải là 6 chữ số")
    private String pin;

    @Size(max = 255, message = "Ghi chú tối đa 255 ký tự")
    private String note;

    public TransferForm() {
    }

    public TransferForm(String toPhoneOrEmail, BigDecimal amount, String pin, String note) {
        this.toPhoneOrEmail = toPhoneOrEmail;
        this.amount = amount;
        this.pin = pin;
        this.note = note;
    }

    public String getToPhoneOrEmail() {
        return toPhoneOrEmail;
    }

    public void setToPhoneOrEmail(String toPhoneOrEmail) {
        this.toPhoneOrEmail = toPhoneOrEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
