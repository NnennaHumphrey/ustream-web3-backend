package com.ustream_web3.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetDTO {

    @NotBlank(message = "New password is required")
    private String newPassword;

    @NotBlank(message = "Confirm new password is required")
    private String confirmPassword;
}
