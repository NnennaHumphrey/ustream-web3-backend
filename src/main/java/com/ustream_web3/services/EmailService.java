package com.ustream_web3.services;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendOtpEmail(String to, String otpCode, String firstName);

    void sendRegistrationConfirmationEmail(String to, String firstName);

    void sendPasswordResetEmail(String to, String token);
}
