package com.ustream_web3.services;

import com.ustream_web3.dtos.VideoDTO;
import com.ustream_web3.entities.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface VideoService {

    List<VideoDTO> getVideosByName (String videoName);

    List<VideoDTO> getAllVideos ();

    List<VideoDTO> getAllVideosByUser(String username);

    String uploadVideo(MultipartFile file, String videoName, String genre) throws Exception;

    String watchVideo(UUID videoId);

    void downloadVideo(UUID videoId, HttpServletResponse response);

    void deleteVideo(UUID videoId);

    void incrementStreamingScore (User user);
}
