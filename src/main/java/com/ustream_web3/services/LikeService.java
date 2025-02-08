package com.ustream_web3.services;

import com.ustream_web3.entities.User;

import java.util.UUID;

public interface LikeService {

    void likeVideo(UUID videoId, User user);

    void unlikeVideo(UUID videoId, User user);

    long countLikesByVideo(UUID videoId);
}
