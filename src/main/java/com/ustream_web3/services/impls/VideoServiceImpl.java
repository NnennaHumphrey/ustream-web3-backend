package com.ustream_web3.services.impls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ustream_web3.constants.UserConstants;
import com.ustream_web3.constants.VideoConstants;
import com.ustream_web3.dtos.VideoDTO;
import com.ustream_web3.entities.Leaderboard;
import com.ustream_web3.entities.Role;
import com.ustream_web3.entities.User;
import com.ustream_web3.entities.Videos;
import com.ustream_web3.exceptions.DownloadingErrorException;
import com.ustream_web3.exceptions.ResourceNotFoundException;
import com.ustream_web3.exceptions.UnauthorizedOperationException;
import com.ustream_web3.mappers.VideoMapper;
import com.ustream_web3.repositories.LeaderboardRepository;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.repositories.VideosRepository;
import com.ustream_web3.services.VideoService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideosRepository videosRepository;
    private final Cloudinary cloudinary;
    private final LeaderboardRepository leaderboardRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;

    public VideoServiceImpl(VideosRepository videosRepository, Cloudinary cloudinary,
                            LeaderboardRepository leaderboardRepository, UserRepository userRepository,
                            VideoMapper videoMapper) {
        this.videosRepository = videosRepository;
        this.cloudinary = cloudinary;
        this.leaderboardRepository = leaderboardRepository;
        this.userRepository = userRepository;
        this.videoMapper = videoMapper;
    }

    @Override
    public List<VideoDTO> getVideosByName(String videoName) {
        return videosRepository.findByVideoNameContainingIgnoreCase(videoName).stream()
                .map(this::convertToVideoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> getAllVideos() {
        return videosRepository.findAll().stream()
                .map(this::convertToVideoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoDTO> getAllVideosByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return videosRepository.findByUser(user).stream()
                .map(this::convertToVideoDTO)
                .collect(Collectors.toList());
    }

    // Convert Video entity to VideoDTO
    private VideoDTO convertToVideoDTO(Videos video) {
        List<String> likes = video.getLikes().stream()
                .map(like -> like.getUser().getUsername())
                .collect(Collectors.toList());

        List<Map<String, String>> comments = video.getComments().stream()
                .map(comment -> {
                    Map<String, String> commentDetails = new HashMap<>();
                    commentDetails.put("username", comment.getUser().getUsername());
                    commentDetails.put("commentText", comment.getText());
                    return commentDetails;
                })
                .collect(Collectors.toList());

        return VideoDTO.builder()
                .videoUrl(video.getVideoUrl())
                .videoName(video.getVideoName())
                .uploadDate(video.getCreatedAt())
                .likes(likes)
                .comments(comments)
                .description(video.getDescription())
                .username(video.getUser().getUsername())
                .build();
    }

    @Override
    public String uploadVideo(MultipartFile file, String videoName, String description) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new UnauthorizedOperationException("File is empty");
        }

        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UnauthorizedOperationException("User not authenticated");
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "video"));
        String videoUrl = uploadResult.get("url").toString();

        VideoDTO videoDTO = VideoDTO.builder()
                .videoUrl(videoUrl)
                .videoName(videoName)
                .description(description)
                .username(user.getUsername())
                .build();

        Videos video = videoMapper.toEntity(videoDTO);

        video.setCreatedAt(LocalDateTime.now());
        video.setUser(user);

        videosRepository.save(video);
        return videoUrl;
    }

    @Override
    public String watchVideo(UUID videoId) {
        incrementStreamingScore(getCurrentLoggedInUser());
        Videos video = videosRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException(VideoConstants.VIDEO_NOT_FOUND));
        return video.getVideoUrl();
    }

    @Override
    public void downloadVideo(UUID videoId, HttpServletResponse response) {

        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UnauthorizedOperationException(UserConstants.UNAUTHORIZED_ACCESS);
        }

        Videos video = videosRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException(VideoConstants.VIDEO_NOT_FOUND));
        String videoUrl = video.getVideoUrl();

        try {
            // Open a connection to the video URL
            URL url = new URL(videoUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();

            response.setHeader("Content-Disposition", "attachment; filename=" + video.getVideoName());
            response.setContentType("video/mp4");

            try (ServletOutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            inputStream.close();
        } catch (IOException e) {
            throw new DownloadingErrorException("Error downloading video");
        }
    }

    @Override
    public void incrementStreamingScore(User user) {
        if (user == null) return;

        Leaderboard leaderboard = leaderboardRepository.findByUser(user).orElseGet(() -> {
            Leaderboard newLeaderBoard = new Leaderboard();
            newLeaderBoard.setUser(user);
            return newLeaderBoard;
        });

        if (leaderboard.getLastScoreReset() == null || leaderboard.getLastScoreReset().isBefore(LocalDateTime.now().minusDays(30))) {

            leaderboard.setScore(1);
            leaderboard.setLastScoreReset(LocalDateTime.now());
        } else {

            leaderboard.setScore(leaderboard.getScore() + 1);
        }

        leaderboardRepository.save(leaderboard);
    }


    @Override
    public void deleteVideo(UUID videoId) {

        User user = getCurrentLoggedInUser();
        if (user == null) {
            throw new UnauthorizedOperationException(UserConstants.UNAUTHORIZED_ACCESS);
        }

        Videos video = videosRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException(VideoConstants.VIDEO_NOT_FOUND));

        if (!video.getUser().equals(user) && !isAdmin(user.getUsername())) {
            throw new UnauthorizedOperationException(UserConstants.UNAUTHORIZED_ACCESS);
        }

        try {
            videosRepository.deleteById(videoId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(VideoConstants.VIDEO_NOT_FOUND);
        }
    }



    private boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(UserConstants.USER_NOT_FOUND));
        return user.getRole().equals(Role.ADMIN);
    }


    private User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username).orElse(null);
            } else if (principal instanceof String) {
                return userRepository.findByUsername((String) principal).orElse(null);
            }
        }
        return null;
    }
}
