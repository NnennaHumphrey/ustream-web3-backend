package com.ustream_web3.repositories;

import com.ustream_web3.entities.Leaderboard;
import com.ustream_web3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, UUID> {
    Optional<Leaderboard> findByUser(User user);
}
