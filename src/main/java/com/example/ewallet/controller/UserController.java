package com.example.ewallet.controller;

import com.example.ewallet.dto.response.ApiResponse;
import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.form.ChangePinForm;
import com.example.ewallet.form.SetPinForm;
import com.example.ewallet.form.UpdateProfileForm;
import com.example.ewallet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        UserResponse data = userService.getCurrentProfile();
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", data));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateCurrentUser(
            @Valid @RequestBody UpdateProfileForm form) {

        UserResponse data = userService.updateCurrentProfile(form);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", data));
    }

    @PostMapping("/me/pin")
    public ResponseEntity<ApiResponse<Void>> setPin(
            @Valid @RequestBody SetPinForm form) {

        userService.setPin(form);
        return ResponseEntity.ok(ApiResponse.success("Đặt mã PIN thành công", null));
    }

    @PutMapping("/me/pin")
    public ResponseEntity<ApiResponse<Void>> changePin(
            @Valid @RequestBody ChangePinForm form) {

        userService.changePin(form);
        return ResponseEntity.ok(ApiResponse.success("Đổi mã PIN thành công", null));
    }
}
