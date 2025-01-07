package com.ustream_web3.mappers;

import com.ustream_web3.dtos.ChangeEmailDTO;
import com.ustream_web3.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ChangeEmailMapper {

    public static User toEntity(ChangeEmailDTO changeEmailDTO, User user) {

        if (changeEmailDTO.getNewEmail() != null && !changeEmailDTO.getNewEmail().isEmpty()) {
            user.setEmail(changeEmailDTO.getNewEmail());
        }
        return user;
    }
}
