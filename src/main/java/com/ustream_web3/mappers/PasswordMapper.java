package com.ustream_web3.mappers;

import com.ustream_web3.dtos.PasswordResetDTO;
import com.ustream_web3.entities.User;

public class PasswordMapper {

    public static User mapToEntity(User user, PasswordResetDTO passwordResetDTO) {
        if (!passwordResetDTO.getNewPassword().equals(passwordResetDTO.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordResetDTO.getNewPassword());
        return user;
    }
}
