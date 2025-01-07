package com.ustream_web3.mappers;

import com.ustream_web3.dtos.VideoDTO;
import com.ustream_web3.entities.Comments;
import com.ustream_web3.entities.Likes;
import com.ustream_web3.entities.Videos;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class VideoMapper {

    public Videos toEntity(VideoDTO videoDTO) {
        if (videoDTO == null) {
            return null;
        }

        Videos video = new Videos();

        video.setVideoUrl(videoDTO.getVideoUrl());
        video.setVideoName(videoDTO.getVideoName());
        video.setDescription(videoDTO.getDescription());

        // Likes (DTO to Entity)
        if (videoDTO.getLikes() != null) {
            List<Likes> likes = new ArrayList<>();
            for (String username : videoDTO.getLikes()) {
                Likes likeEntity = new Likes();
                likeEntity.setVideo(video);
            }
            video.setLikes(likes);
        }

        // Comments (DTO to Entity)
        if (videoDTO.getComments() != null) {
            List<Comments> comments = new ArrayList<>();
            for (Map<String, String> commentMap : videoDTO.getComments()) {
                Comments commentEntity = new Comments();
                commentEntity.setText(commentMap.get("comment"));
                commentEntity.setVideo(video);
                comments.add(commentEntity);
            }
            video.setComments(comments);
        }

        return video;
    }

    public VideoDTO toDTO(Videos video) {
        if (video == null) {
            return null;
        }

        VideoDTO videoDTO = new VideoDTO();

        // Map basic fields
        videoDTO.setVideoUrl(video.getVideoUrl());
        videoDTO.setVideoName(video.getVideoName());
        videoDTO.setUploadDate(video.getCreatedAt());
        videoDTO.setDescription(video.getDescription());

        // Likes (Entity to DTO)
        if (video.getLikes() != null) {
            List<String> likes = new ArrayList<>();
            for (Likes like : video.getLikes()) {
                if (like.getUser() != null) {
                    likes.add(like.getUser().getUsername());
                }
            }
            videoDTO.setLikes(likes);
        }

        // Comments (Entity to DTO)
        if (video.getComments() != null) {
            List<Map<String, String>> comments = new ArrayList<>();
            for (Comments comment : video.getComments()) {
                if (comment.getUser() != null) {
                    comments.add(Map.of(
                            "username", comment.getUser().getUsername(),
                            "comment", comment.getText()
                    ));
                }
            }
            videoDTO.setComments(comments);
        }

        // Map user to username
        if (video.getUser() != null) {
            videoDTO.setUsername(video.getUser().getUsername());
        }

        return videoDTO;
    }
}
