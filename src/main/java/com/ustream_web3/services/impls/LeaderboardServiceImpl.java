package com.ustream_web3.services.impls;

import com.ustream_web3.dtos.LeaderboardDTO;
import com.ustream_web3.entities.Leaderboard;
import com.ustream_web3.mappers.LeaderboardMapper;
import com.ustream_web3.repositories.LeaderboardRepository;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    private final LeaderboardRepository leaderboardEntryRepository;
    private final LeaderboardMapper leaderboardMapper;

    @Autowired
    public LeaderboardServiceImpl(LeaderboardRepository leaderboardEntryRepository,
                                  UserRepository userRepository,
                                  LeaderboardMapper leaderboardMapper) {
        this.leaderboardEntryRepository = leaderboardEntryRepository;
        this.leaderboardMapper = leaderboardMapper;
    }

    @Override
    public List<LeaderboardDTO> getLeaderboard() {
        List<Leaderboard> entries = leaderboardEntryRepository.findAll();

        return entries.stream()
                .map(entry -> leaderboardMapper.toDTO(entry)) // Use the mapper
                .collect(Collectors.toList());
    }
}
