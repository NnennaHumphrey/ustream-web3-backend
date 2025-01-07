package com.ustream_web3.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserProfileService {

    String changeUsername(String newUsername);

    String changeEmail(String newEmail);

    byte [] uploadPhoto( MultipartFile photo) throws IOException;

    String changePassword(String currentPassword, String newPassword, String confirmPassword);

}
