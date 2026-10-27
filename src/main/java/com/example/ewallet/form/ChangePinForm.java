package com.example.ewallet.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ChangePinForm {
    @NotBlank(message = "Mã PIN cũ không được để trống")
    @Pattern(regexp = "^\\d{6}$", message = "Mã PIN cũ phải là 6 chữ số")
    private String oldPin;

    @NotBlank(message = "Mã PIN mới không được để trống")
    @Pattern(regexp = "^\\d{6}$", message = "Mã PIN mới phải là 6 chữ số")
    private String newPin;

    @NotBlank(message = "Xác nhận mã PIN mới không được để trống")
    @Pattern(regexp = "^\\d{6}$", message = "Xác nhận mã PIN mới phải là 6 chữ số")
    private String confirmNewPin;

    public ChangePinForm() {
    }

    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public String getConfirmNewPin() {
        return confirmNewPin;
    }

    public void setConfirmNewPin(String confirmNewPin) {
        this.confirmNewPin = confirmNewPin;
    }
}
