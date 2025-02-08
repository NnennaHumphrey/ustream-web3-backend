package com.ustream_web3.services.impls;

import com.ustream_web3.entities.Likes;
import com.ustream_web3.entities.User;
import com.ustream_web3.entities.Videos;
import com.ustream_web3.exceptions.ResourceNotFoundException;
import com.ustream_web3.repositories.LikesRepository;
import com.ustream_web3.repositories.VideosRepository;
import com.ustream_web3.services.LikeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikesRepository likesRepository;
    private final VideosRepository videoRepository;

    public LikeServiceImpl(LikesRepository likesRepository, VideosRepository videoRepository) {
        this.likesRepository = likesRepository;
        this.videoRepository = videoRepository;
    }

    @Override
    public void likeVideo(UUID videoId, User user) {
        Videos video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));

        likesRepository.findByVideoIdAndUserId(videoId, user.getId())
                .ifPresent(like -> {
                    throw new RuntimeException("Already liked");
                });

        Likes like = new Likes();
        like.setVideo(video);
        like.setUser(user);
        likesRepository.save(like);
    }

    @Override
    public void unlikeVideo(UUID videoId, User user) {
        Likes like = likesRepository.findByVideoIdAndUserId(videoId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));

        likesRepository.delete(like);
    }

    @Override
    public long countLikesByVideo(UUID videoId) {
        return likesRepository.countByVideoId(videoId);
    }
}
