package com.ustream_web3.services.impls;

import com.ustream_web3.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);
        } catch (MailException e) {
            logger.error("Failed to send email to {}. Error: {}", to, e.getMessage(), e);
        }
    }

    @Override
    @Async
    @Transactional
    public void sendOtpEmail(String to, String otpCode, String firstName) {
        String subject = "Email Verification - OTP Code";
        String text = "Dear " + firstName + ",\n\n" +
                "Your One-Time Password (OTP) for email verification is: " + otpCode + ".\n\n" +
                "Please use this code to complete your verification process. The OTP is valid for a limited time.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Ustream Team";
        sendSimpleMessage(to, subject, text);
    }


    @Override
    @Async
    @Transactional
    public void sendRegistrationConfirmationEmail(String to, String firstName) {
        String subject = "Registration Confirmation";
        String text = "Dear " + firstName + ",\n\n" +
                "Thank you for registering with us. We are excited to have you on board. Your account has been created successfully.\n\n" +
                "If you have any questions or need assistance, please don't hesitate to reach out to us.\n\n" +
                "Best regards,\n" +
                "Ustream Team";
        sendSimpleMessage(to, subject, text);
    }


    @Override
    @Async
    @Transactional
    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String text = "To reset your password, please click the following link: "
                + "http://yourdomain.com/reset-password?token=" + token;
        sendSimpleMessage(to, subject, text);
    }
}
