package com.ustream_web3.services;

import com.ustream_web3.dtos.LeaderboardDTO;

import java.util.List;

public interface LeaderboardService {

    List<LeaderboardDTO> getLeaderboard();
}
