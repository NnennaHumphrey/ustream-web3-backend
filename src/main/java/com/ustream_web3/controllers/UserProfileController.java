package com.ustream_web3.controllers;

import com.ustream_web3.constants.UserConstants;
import com.ustream_web3.dtos.ChangeEmailDTO;
import com.ustream_web3.dtos.ChangePasswordDTO;
import com.ustream_web3.dtos.ChangeUsernameDTO;
import com.ustream_web3.dtos.ResponseDTO;
import com.ustream_web3.entities.User;
import com.ustream_web3.services.UserProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
@AllArgsConstructor
@RestController
@Validated
@RequestMapping(path ="/api/v1/user/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PutMapping("/change-username")
    public ResponseEntity<ResponseDTO> changeUsername(@RequestBody @Valid ChangeUsernameDTO changeUsernameDTO) {
        userProfileService.changeUsername(changeUsernameDTO.getNewUsername());

        ResponseDTO responseDTO = new ResponseDTO(
                UserConstants.StatusCode.OK.getCode(),
                UserConstants.USER_USERNAME

        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/change-email")
    public ResponseEntity<ResponseDTO> changeEmail(@RequestBody @Valid ChangeEmailDTO changeEmailDTO) {
         userProfileService.changeEmail(changeEmailDTO.getNewEmail());

        ResponseDTO responseDTO = new ResponseDTO(
                UserConstants.StatusCode.OK.getCode(),
                UserConstants.USER_EMAIL
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        userProfileService.changePassword(changePasswordDTO.getCurrentPassword(),
                changePasswordDTO.getNewPassword(),
                changePasswordDTO.getConfirmPassword());

        ResponseDTO responseDTO = new ResponseDTO(
                UserConstants.StatusCode.OK.getCode(),
                UserConstants.USER_PASSWORD
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<ResponseDTO> uploadPhoto(@RequestParam("photo") MultipartFile photo) throws IOException {
        byte[] uploadedPhoto = userProfileService.uploadPhoto(photo);

        ResponseDTO responseDTO = new ResponseDTO(
                UserConstants.StatusCode.OK.getCode(),
                UserConstants.USER_PHOTO
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
