package com.ustream_web3.utils;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Random;

@Configuration
public class OtpUtils {

    private static final int OTP_LENGTH = 6;


    public String generateOtpCode() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }


    public LocalDateTime generateOtpExpiryDate() {
        return LocalDateTime.now().plusMinutes(5);
    }

    public boolean isOtpExpired(LocalDateTime expiryDate) {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
