package com.ustream_web3.controllers;


import com.ustream_web3.constants.VideoConstants;
import com.ustream_web3.dtos.ResponseDTO;
import com.ustream_web3.dtos.VideoDTO;
import com.ustream_web3.services.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/videos", produces = {MediaType.APPLICATION_JSON_VALUE})
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/search")
    public ResponseEntity<List<VideoDTO>> getVideosByName(@RequestParam("videoName") String videoName) {
        List<VideoDTO> videoResponseList = videoService.getVideosByName(videoName);
        return ResponseEntity.ok(videoResponseList);
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO> uploadVideo(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("videoName") String videoName,
                                                   @RequestParam("description") String description) {
        try {
            String videoUrl = videoService.uploadVideo(file, videoName, description);
            ResponseDTO responseDTO = new ResponseDTO(
                    VideoConstants.StatusCode.OK.getCode(),
                    VideoConstants.VIDEO_UPLOADED
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO =  new ResponseDTO(
                    VideoConstants.StatusCode.BAD_REQUEST.getCode(),
                    VideoConstants.VIDEO_UPLOAD_FAILED
            );
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<VideoDTO>> getAllVideos() {
        List<VideoDTO> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @DeleteMapping("/delete/{videoId}")
    public ResponseEntity<ResponseDTO> deleteVideo(@PathVariable UUID videoId) {
        try{
            videoService.deleteVideo(videoId);
            ResponseDTO responseDTO = new ResponseDTO(
                    VideoConstants.StatusCode.OK.getCode(),
                    VideoConstants.VIDEO_DELETED
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDTO);

        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(
                    VideoConstants.StatusCode.BAD_REQUEST.getCode(),
                    VideoConstants.VIDEO_DELETE_FAILED
            );
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }
    }


    @GetMapping("/watch/{videoId}")
    public ResponseEntity<ResponseDTO> watchVideo(@PathVariable UUID videoId) {
        try {
            String videoUrl = videoService.watchVideo(videoId);
            ResponseDTO responseDTO = new ResponseDTO(
                    VideoConstants.StatusCode.OK.getCode(),
                    VideoConstants.VIDEO_RETRIEVED
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDTO);
        } catch (Exception e){
            ResponseDTO responseDTO = new ResponseDTO(
                    VideoConstants.StatusCode.NOT_FOUND.getCode(),
                    VideoConstants.VIDEO_NOT_FOUND
            );
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(responseDTO);
        }
    }

    @GetMapping("/download/{videoId}")
    public ResponseEntity<ResponseDTO> downloadVideo(@PathVariable UUID videoId, HttpServletResponse response) throws IOException {
        try {
            videoService.downloadVideo(videoId, response);
            ResponseDTO responseDTO = new ResponseDTO(
                    VideoConstants.StatusCode.OK.getCode(),
                    VideoConstants.VIDEO_DOWNLOADED
            );
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseDTO);
        } catch(Exception e) {
            ResponseDTO responseDTO = new ResponseDTO(
                    VideoConstants.StatusCode.BAD_REQUEST.getCode(),
                    VideoConstants.VIDEO_DOWNLOAD_FAILED
            );
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(responseDTO);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<VideoDTO>> getAllVideosByUser(@PathVariable String username) {
        List<VideoDTO> videos = videoService.getAllVideosByUser(username);
        return ResponseEntity.ok(videos);
    }
}
