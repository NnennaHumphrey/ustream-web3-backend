package com.ustream_web3.services.impls;

import com.ustream_web3.constants.UserConstants;
import com.ustream_web3.dtos.PasswordResetDTO;
import com.ustream_web3.entities.PasswordReset;
import com.ustream_web3.entities.User;
import com.ustream_web3.exceptions.InvalidCredentialsException;
import com.ustream_web3.exceptions.TokenExpiredException;
import com.ustream_web3.exceptions.UserNotFoundException;
import com.ustream_web3.mappers.PasswordMapper;
import com.ustream_web3.repositories.PasswordResetRepository;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.EmailService;
import com.ustream_web3.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));

        String token = UUID.randomUUID().toString();
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUser(user);
        passwordReset.setToken(token);
        passwordReset.setExpiryDate(LocalDateTime.now().plusHours(1));

        passwordResetRepository.save(passwordReset);

        emailService.sendPasswordResetEmail(user.getEmail(), token);

    }

    @Override
    public void resetPassword(String token, PasswordResetDTO passwordResetDto) {

        PasswordReset resetToken = passwordResetRepository.findByToken(token)
               .orElseThrow(() -> new InvalidCredentialsException(UserConstants.INVALID_TOKEN));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(UserConstants.TOKEN_EXPIRED);
        }

        User user = resetToken.getUser();
        user = PasswordMapper.mapToEntity(user, passwordResetDto);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        passwordResetRepository.delete(resetToken);
    }

}
