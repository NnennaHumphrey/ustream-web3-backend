package com.ustream_web3.controllers;

import com.ustream_web3.constants.UserConstants;
import com.ustream_web3.dtos.LoginDTO;
import com.ustream_web3.dtos.RegistrationDTO;
import com.ustream_web3.dtos.ResponseDTO;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.AuthService;
import com.ustream_web3.services.OtpService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(path ="/api/auth",  produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        authService.registerUser(registrationDTO);

        ResponseDTO responseDTO = new ResponseDTO(
                UserConstants.StatusCode.CREATED.getCode(),
                UserConstants.USER_CREATED
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);

    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout() {
        authService.logout();

        ResponseDTO responseDTO = new ResponseDTO(
                UserConstants.StatusCode.OK.getCode(),
                UserConstants.USER_LOGGED_OUT

        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        String token = authService.loginUser(loginDTO);

        // Prepare response map
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", UserConstants.StatusCode.OK.getCode());
        response.put("statusMessage", UserConstants.USER_LOGGED_IN);
        response.put("token", token);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }



}
