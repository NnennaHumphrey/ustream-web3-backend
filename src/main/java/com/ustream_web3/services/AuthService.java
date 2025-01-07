package com.ustream_web3.services;

import com.ustream_web3.dtos.LoginDTO;
import com.ustream_web3.dtos.RegistrationDTO;

public interface AuthService {
    void registerUser(RegistrationDTO registrationDTO);

    String loginUser(LoginDTO loginDTO);

    void logout();
}
