package com.ustream_web3.mappers;

import com.ustream_web3.dtos.ChangeUsernameDTO;
import com.ustream_web3.entities.User;
import org.springframework.stereotype.Component;

@Component
public class ChangeUsernameMapper {

    public static User toEntity(ChangeUsernameDTO changeUsernameDTO, User user) {

        if (changeUsernameDTO.getNewUsername() != null && !changeUsernameDTO.getNewUsername().isEmpty()) {
            user.setUsername(changeUsernameDTO.getNewUsername());
        }
        return user;
    }
}
