package com.example.ewallet.form;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TopupForm {

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "0.01", inclusive = true,
            message = "Số tiền phải lớn hơn 0")
    @Digits(integer = 17, fraction = 2,
            message = "Số tiền chỉ được có tối đa 2 chữ số thập phân")
    private BigDecimal amount;

    @Size(max = 255, message = "Ghi chú tối đa 255 ký tự")
    private String note;

    public TopupForm() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
