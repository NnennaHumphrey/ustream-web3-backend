package com.ustream_web3.controllers;

import com.ustream_web3.constants.PasswordConstants;
import com.ustream_web3.dtos.PasswordResetDTO;
import com.ustream_web3.dtos.ResponseDTO;
import com.ustream_web3.services.PasswordResetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path ="/api/password-reset", produces = {MediaType.APPLICATION_JSON_VALUE})
public class PasswordResetController {


        @Autowired
        private PasswordResetService passwordResetService;

        @PostMapping("/request")
        public ResponseEntity<ResponseDTO> requestPasswordReset(@RequestParam String email) {
            try {
                passwordResetService.requestPasswordReset(email);
                ResponseDTO response = new ResponseDTO(
                        PasswordConstants.StatusCode.CREATED.getCode(),
                        PasswordConstants.PASSWORD_RESET_REQUEST
                );
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (RuntimeException e) {
                log.error("Error requesting password reset for email: {}", email, e);
                ResponseDTO response = new ResponseDTO(
                        PasswordConstants.StatusCode.BAD_REQUEST.getCode(),
                        PasswordConstants.getErrorMessage("PASSWORD_RESET_FAILED")
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

        @PostMapping("/reset")
        public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String token, @RequestBody PasswordResetDTO passwordResetDto) {
        log.info("Received password reset request for token: {}", token);

            try {
                passwordResetService.resetPassword(token, passwordResetDto);
//            log.info("Password has been reset successfully for token: {}", token);

                ResponseDTO response = new ResponseDTO(
                        PasswordConstants.StatusCode.OK.getCode(),
                        PasswordConstants.PASSWORD_RESET_SUCCESS
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (RuntimeException e) {
//            log.error("Error resetting password for token: {}. Error: {}", token, e.getMessage());


                ResponseDTO response = new ResponseDTO(
                        PasswordConstants.StatusCode.BAD_REQUEST.getCode(),
                        PasswordConstants.getErrorMessage("PASSWORD_TOKEN_INVALID")
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
//            log.error("Unexpected error occurred while resetting password for token: {}. Error: {}", token, e.getMessage());


                ResponseDTO response = new ResponseDTO(
                        PasswordConstants.StatusCode.INTERNAL_SERVER_ERROR.getCode(),
                        "Unexpected error occurred while resetting the password."
                );
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
