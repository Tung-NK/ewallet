package com.example.ewallet.service;

import com.example.ewallet.dto.response.TransferResponse;
import com.example.ewallet.form.TransferForm;

public interface TransferService {
    TransferResponse transfer(TransferForm form);

}
