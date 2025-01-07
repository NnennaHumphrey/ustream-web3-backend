package com.ustream_web3.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpVerificationDTO {

    @NotBlank(message = "OTP is required")
    private String otp;

    public OtpVerificationDTO(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
