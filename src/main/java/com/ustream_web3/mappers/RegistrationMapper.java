package com.ustream_web3.mappers;

import com.ustream_web3.dtos.RegistrationDTO;
import com.ustream_web3.entities.User;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {
    public User toEntity(RegistrationDTO registrationDTO) {
        if (registrationDTO == null) {
            return null; // Handle null case if needed
        }

        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        return user;
    }

}
