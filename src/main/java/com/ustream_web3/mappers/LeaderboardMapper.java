package com.ustream_web3.mappers;

import com.ustream_web3.dtos.LeaderboardDTO;
import com.ustream_web3.entities.Leaderboard;
import com.ustream_web3.entities.User;
import org.springframework.stereotype.Component;

@Component
public class LeaderboardMapper {

    public LeaderboardDTO toDTO(Leaderboard leaderboard) {
        if (leaderboard == null || leaderboard.getUser() == null) {
            return null;
        }

        return LeaderboardDTO.builder()
                .firstName(leaderboard.getUser().getFirstName())
                .lastName(leaderboard.getUser().getLastName())
                .score(leaderboard.getScore())
                .profilePictureUrl(leaderboard.getUser().getProfilePictureUrl())
                .build();
    }

    public Leaderboard toEntity(LeaderboardDTO leaderboardDTO, User user) {
        if (leaderboardDTO == null || user == null) {
            return null;
        }

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setUser(user);
        leaderboard.setScore(leaderboardDTO.getScore());

        return leaderboard;
    }
}
