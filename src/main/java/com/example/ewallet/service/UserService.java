package com.example.ewallet.service;

import com.example.ewallet.dto.response.UserResponse;
import com.example.ewallet.form.ChangePinForm;
import com.example.ewallet.form.SetPinForm;
import com.example.ewallet.form.UpdateProfileForm;

public interface UserService {
    UserResponse getCurrentProfile();

    UserResponse updateCurrentProfile(UpdateProfileForm form);

    void setPin(SetPinForm form);

    void changePin(ChangePinForm form);
}
