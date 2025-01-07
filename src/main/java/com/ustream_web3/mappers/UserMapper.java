//package com.ustream_web3.mappers;
//
//import com.ustream_web3.dtos.UserDTO;
//import com.ustream_web3.entities.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "role", target = "role")
//    User toEntity(UserDTO userDTO);
//}
//
