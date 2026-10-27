package com.example.ewallet.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SetPinForm {
    @NotBlank(message = "Mã PIN không được để trống")
    @Pattern(regexp = "^\\d{6}$", message = "Mã PIN phải là 6 chữ số")
    private String pin;

    @NotBlank(message = "Xác nhận mã PIN không được để trống")
    @Pattern(regexp = "^\\d{6}$", message = "Xác nhận mã PIN phải là 6 chữ số")
    private String confirmPin;

    public SetPinForm() {
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getConfirmPin() {
        return confirmPin;
    }

    public void setConfirmPin(String confirmPin) {
        this.confirmPin = confirmPin;
    }
}
