package com.ustream_web3.controllers;

import com.ustream_web3.constants.UserConstants;
import com.ustream_web3.dtos.OtpVerificationDTO;
import com.ustream_web3.dtos.ResponseDTO;
import com.ustream_web3.entities.User;
import com.ustream_web3.exceptions.UserNotFoundException;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.OtpService;
import com.ustream_web3.services.impls.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path ="/api/email",  produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class EmailVerificationController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseDTO> verifyOtp(@Valid @RequestBody OtpVerificationDTO request,
                                                 @RequestParam String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user == null) {
            ResponseDTO response = new ResponseDTO(
                    UserConstants.StatusCode.UNAUTHORIZED.getCode(),
                    UserConstants.USER_NOT_FOUND
            );
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        System.out.println("Authenticated User: " + user.getUsername());
        boolean isVerified = otpService.verifyOtp(request, user);
        ResponseDTO response = isVerified
                ? new ResponseDTO(UserConstants.StatusCode.OK.getCode(), UserConstants.OTP_VERIFIED)
                : new ResponseDTO(UserConstants.StatusCode.UNAUTHORIZED.getCode(), UserConstants.OTP_NOT_VERIFIED);

        return ResponseEntity.status(isVerified ? HttpStatus.OK : HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestParam("username") String username) {
        otpService.resendOtp(username);

        ResponseDTO response = new ResponseDTO(
                UserConstants.StatusCode.OK.getCode(),
                UserConstants.OTP_RESENT
        );
         return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(response);
  }

}
