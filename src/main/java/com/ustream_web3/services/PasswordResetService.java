package com.ustream_web3.services;

import com.ustream_web3.dtos.PasswordResetDTO;

public interface PasswordResetService {

    void requestPasswordReset(String email);

    void resetPassword(String token,  PasswordResetDTO passwordResetDto);


}
