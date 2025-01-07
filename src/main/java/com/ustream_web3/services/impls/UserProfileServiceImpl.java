package com.ustream_web3.services.impls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ustream_web3.constants.PasswordConstants;
import com.ustream_web3.constants.UserConstants;
import com.ustream_web3.dtos.ChangeEmailDTO;
import com.ustream_web3.dtos.ChangePasswordDTO;
import com.ustream_web3.dtos.ChangeUsernameDTO;
import com.ustream_web3.entities.User;
import com.ustream_web3.exceptions.EmailAlreadyExistException;
import com.ustream_web3.exceptions.InvalidCredentialsException;
import com.ustream_web3.exceptions.UnauthorizedOperationException;
import com.ustream_web3.exceptions.UserNotFoundException;
import com.ustream_web3.mappers.ChangeEmailMapper;
import com.ustream_web3.mappers.ChangePasswordMapper;
import com.ustream_web3.mappers.ChangeUsernameMapper;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ChangeEmailMapper changeEmailMapper;

    @Autowired
    private ChangeUsernameMapper changeUsernameMapper;

    @Autowired
    private ChangePasswordMapper changePasswordMapper;

    @Override
    public String changeUsername(String newUsername) {
        User authenticatedUser = getAuthenticatedUser(); // No need for currentUsername here, as it's retrieved automatically

        if (!isAuthorizedToChangeUsername(authenticatedUser, authenticatedUser.getUsername())) {
            throw new UnauthorizedOperationException(UserConstants.UNAUTHORIZED_ACCESS);
        }

        if (isUsernameTaken(newUsername)) {
            throw new UserNotFoundException(UserConstants.USERNAME_TAKEN);
        }

        ChangeUsernameDTO changeUsernameDTO = new ChangeUsernameDTO(newUsername);
        authenticatedUser = changeUsernameMapper.toEntity(changeUsernameDTO, authenticatedUser);
        userRepository.save(authenticatedUser);

        return UserConstants.USER_USERNAME;
    }


    @Override
    public String changeEmail(String newEmail) {
        User authenticatedUser = getAuthenticatedUser(); // No need for username here, it's retrieved from authentication

        if (!isAuthorizedToChangeEmail(authenticatedUser, authenticatedUser.getUsername())) {
            throw new UnauthorizedOperationException(UserConstants.UNAUTHORIZED_ACCESS);
        }

        if (isEmailAlreadyRegistered(newEmail)) {
            throw new EmailAlreadyExistException(UserConstants.EMAIL_ALREADY_REGISTERED);
        }

        ChangeEmailDTO changeEmailDTO = new ChangeEmailDTO(newEmail);
        authenticatedUser = changeEmailMapper.toEntity(changeEmailDTO, authenticatedUser);
        userRepository.save(authenticatedUser);

        return UserConstants.USER_EMAIL;
    }


    @Override
    public byte[] uploadPhoto(MultipartFile photo) throws IOException {
        User authenticatedUser = getAuthenticatedUser();

        if (!isAuthorizedToUploadPhoto(authenticatedUser, authenticatedUser.getUsername())) {
            throw new UnauthorizedOperationException("You are not authorized to upload a photo for this user.");
        }

        if (photo.isEmpty()) {
            throw new InvalidCredentialsException("Uploaded photo is empty");
        }

        Map<String, String> uploadResult = cloudinary.uploader().upload(photo.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        String photoUrl = uploadResult.get("secure_url");

        authenticatedUser.setProfilePictureUrl(photoUrl);
        userRepository.save(authenticatedUser);

        URL url = new URL(photoUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }

    @Override
    public String changePassword(String currentPassword, String newPassword, String confirmPassword) {
        User authenticatedUser = getAuthenticatedUser();

        if (!passwordEncoder.matches(currentPassword, authenticatedUser.getPassword())) {
            throw new InvalidCredentialsException("Current password is incorrect.");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidCredentialsException("New password and confirm password do not match.");
        }

        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO(newPassword);
        authenticatedUser = changePasswordMapper.toEntity(changePasswordDTO, authenticatedUser);
        authenticatedUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(authenticatedUser);

        return PasswordConstants.PASSWORD_CHANGED;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(UserConstants.USER_NOT_FOUND));
    }

    private boolean isAuthorizedToChangeUsername(User authenticatedUser, String currentUsername) {
        return authenticatedUser.getUsername().equals(currentUsername);
    }

    private boolean isUsernameTaken(String newUsername) {
        return userRepository.findByUsername(newUsername).isPresent();
    }

    private boolean isAuthorizedToChangeEmail(User authenticatedUser, String username) {
        return authenticatedUser.getUsername().equals(username);
    }

    private boolean isEmailAlreadyRegistered(String newEmail) {
        return userRepository.findByEmail(newEmail).isPresent();
    }

    private boolean isAuthorizedToUploadPhoto(User authenticatedUser, String username) {
        return authenticatedUser.getUsername().equals(username);
    }
}
