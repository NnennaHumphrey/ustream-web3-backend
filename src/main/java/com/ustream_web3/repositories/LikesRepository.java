package com.ustream_web3.repositories;

import com.ustream_web3.entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikesRepository extends JpaRepository<Likes, UUID> {

    List<Likes> findByVideoId(UUID videoId);


    Likes findByUserIdAndVideoId(UUID userId, UUID videoId);

    Optional<Likes> findByVideoIdAndUserId(UUID videoId, UUID id);

    long countByVideoId(UUID videoId);


}
