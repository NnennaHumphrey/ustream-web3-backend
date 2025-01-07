package com.ustream_web3.dtos;

import com.ustream_web3.entities.Role;
import lombok.Data;

@Data
public class UserDTO {

    private String id;
    private String username;
    private String email;
    private Role role;
}
