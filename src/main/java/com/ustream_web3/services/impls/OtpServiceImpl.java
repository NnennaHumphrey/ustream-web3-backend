package com.ustream_web3.services.impls;

import com.ustream_web3.constants.UserConstants;
import com.ustream_web3.dtos.OtpVerificationDTO;
import com.ustream_web3.entities.Otp;
import com.ustream_web3.entities.User;
import com.ustream_web3.exceptions.*;
import com.ustream_web3.repositories.OtpRepository;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.EmailService;
import com.ustream_web3.services.OtpService;
import com.ustream_web3.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final OtpUtils otpUtils;
    private final EmailService emailService;

    @Autowired
    public OtpServiceImpl(OtpRepository otpRepository, UserRepository userRepository,
                          OtpUtils otpUtils, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.otpUtils = otpUtils;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public Otp generateAndSaveOtp(User user) {
        validateUser(user);
        Otp otp = createOrUpdateOtpForUser(user);
        emailService.sendOtpEmail(user.getEmail(), otp.getOtpCode(), user.getFirstName());
        return otpRepository.save(otp);
    }

    @Override
    public boolean isOtpExpired(User user) {
        validateUser(user);
        Otp otp = getOtpForUser(user);
        return otpUtils.isOtpExpired(otp.getOtpExpiryDate());
    }

    @Override
    public void resendOtp(String  username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));

        if (user.isVerified()) {
            throw new AlreadyVerifiedException(UserConstants.USER_ALREADY_VERIFIED);
        }
        Otp newOtp = createOrUpdateOtpForUser(user);
        otpRepository.save(newOtp);
        emailService.sendOtpEmail(user.getEmail(), newOtp.getOtpCode(), user.getFirstName());
    }

    @Override
    @Transactional
    public boolean verifyOtp(OtpVerificationDTO otpVerificationDTO, User user) {
        validateUser(user);
        validateOtpInput(otpVerificationDTO);

        Otp otp = otpRepository.findByOtpCodeAndUser(otpVerificationDTO.getOtp(), user)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid OTP."));

        if (otpUtils.isOtpExpired(otp.getOtpExpiryDate())) {
            throw new OtpExpiredException ("OTP has expired.");
        }

        user.setVerified(true);
        userRepository.save(user);

        emailService.sendRegistrationConfirmationEmail(user.getEmail(), user.getFirstName());
        return true;
    }

    private Otp createOrUpdateOtpForUser(User user) {
        Otp otp = otpRepository.findByUser(user).orElse(new Otp());
        otp.setUser(user);
        otp.setEmail(user.getEmail());
        otp.setOtpCode(otpUtils.generateOtpCode());
        otp.setOtpExpiryDate(otpUtils.generateOtpExpiryDate());
        return otp;
    }

    private Otp getOtpForUser(User user) {
        return otpRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found for user."));
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
    }

    private void validateOtpInput(OtpVerificationDTO otpVerificationDTO) {
        if (otpVerificationDTO == null || otpVerificationDTO.getOtp() == null || otpVerificationDTO.getOtp().isEmpty()) {
            throw new InvalidCredentialsException("Invalid OTP input.");
        }
    }
}
