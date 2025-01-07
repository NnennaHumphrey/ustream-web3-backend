package com.ustream_web3.repositories;

import com.ustream_web3.entities.User;
import com.ustream_web3.entities.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VideosRepository extends JpaRepository<Videos, UUID> {

    List<Videos> findByVideoNameContainingIgnoreCase(String videoName);

    List<Videos> findByUser(User user);
}
