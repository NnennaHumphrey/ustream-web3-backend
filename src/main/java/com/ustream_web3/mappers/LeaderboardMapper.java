//package com.ustream_web3.mappers;
//
//import com.ustream_web3.dtos.LeaderboardDTO;
//import com.ustream_web3.entities.Leaderboard;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface LeaderboardMapper {
//    LeaderboardMapper INSTANCE = Mappers.getMapper(LeaderboardMapper.class);
//
//    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(source = "lastName", target = "lastName")
//    @Mapping(source = "score", target = "score")
//    @Mapping(source = "profilePictureUrl", target = "profilePictureUrl")
//    Leaderboard toEntity(LeaderboardDTO leaderboardDTO);
//}
//
