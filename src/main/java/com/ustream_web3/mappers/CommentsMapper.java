//package com.ustream_web3.mappers;
//
//import com.ustream_web3.dtos.CommentDTO;
//import com.ustream_web3.entities.Comments;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface CommentsMapper {
//    CommentsMapper INSTANCE = Mappers.getMapper(CommentsMapper.class);
//
//    @Mapping(source = "username", target = "username")
//    @Mapping(source = "text", target = "text")
//    Comments toEntity(CommentDTO commentDTO);
//}
//
