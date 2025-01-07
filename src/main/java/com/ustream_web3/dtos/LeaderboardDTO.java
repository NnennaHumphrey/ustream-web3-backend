package com.ustream_web3.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardDTO {

    private String firstName;

    private String lastName;

    private int score;

    private String profilePictureUrl;
}


