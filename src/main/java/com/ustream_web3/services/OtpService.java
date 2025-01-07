package com.ustream_web3.services;

import com.ustream_web3.dtos.OtpVerificationDTO;
import com.ustream_web3.entities.Otp;
import com.ustream_web3.entities.User;

public interface OtpService {

    Otp generateAndSaveOtp(User user);

    boolean verifyOtp(OtpVerificationDTO otpVerificationDTO, User user);

    boolean isOtpExpired(User user);

    void resendOtp(String username);

}
