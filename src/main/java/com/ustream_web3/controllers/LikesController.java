package com.ustream_web3.controllers;

import com.ustream_web3.constants.LikeConstants;
import com.ustream_web3.dtos.ResponseDTO;
import com.ustream_web3.entities.User;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.services.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path ="/api/likes",  produces = {MediaType.APPLICATION_JSON_VALUE})
public class LikesController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/like/{videoId}")
    public ResponseEntity<ResponseDTO> likeVideo(@PathVariable UUID videoId, @RequestParam String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            likeService.likeVideo(videoId, user);


            ResponseDTO response = new ResponseDTO(
                    LikeConstants.StatusCode.CREATED.getCode(),
                    LikeConstants.LIKE_ADDED
            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {

            ResponseDTO response = new ResponseDTO(
                    LikeConstants.StatusCode.BAD_REQUEST.getCode(),
                    LikeConstants.getErrorMessage("LIKE_ERROR")
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unlike/{videoId}")
    public ResponseEntity<ResponseDTO> unlikeVideo(@PathVariable UUID videoId, @RequestParam String username) {

        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            likeService.unlikeVideo(videoId, user);


            ResponseDTO response = new ResponseDTO(
                    LikeConstants.StatusCode.NO_CONTENT.getCode(),
                    LikeConstants.LIKE_REMOVED
            );

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Return error response using constants
            ResponseDTO response = new ResponseDTO(
                    LikeConstants.StatusCode.BAD_REQUEST.getCode(),
                    LikeConstants.getErrorMessage("LIKE_REMOVAL_FAILED")
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/count/{videoId}")
    public ResponseEntity<ResponseDTO> countLikes(@PathVariable UUID videoId) {
        try {
            long likeCount = likeService.countLikesByVideo(videoId);


            ResponseDTO response = new ResponseDTO(
                    LikeConstants.StatusCode.OK.getCode(),
                    LikeConstants.LIKE_FETCHED
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {

            ResponseDTO response = new ResponseDTO(
                    LikeConstants.StatusCode.INTERNAL_SERVER_ERROR.getCode(),
                    "Error fetching like count"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
