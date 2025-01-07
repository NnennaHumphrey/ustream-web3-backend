package com.ustream_web3.mappers;

import com.ustream_web3.dtos.ChangePasswordDTO;
import com.ustream_web3.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordMapper {

    private final PasswordEncoder passwordEncoder;

    public ChangePasswordMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(ChangePasswordDTO changePasswordDTO, User user) {
        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("New passwords do not match");
        }
        String encodedPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        user.setPassword(encodedPassword);

        return user;
    }
}
