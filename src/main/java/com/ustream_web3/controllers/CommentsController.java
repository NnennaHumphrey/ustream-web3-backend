package com.ustream_web3.controllers;

import com.ustream_web3.constants.CommentConstants;
import com.ustream_web3.dtos.CommentDTO;
import com.ustream_web3.dtos.ResponseDTO;
import com.ustream_web3.services.CommentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/comments", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;


    @PostMapping("/add/{videoId}")
    public ResponseEntity<ResponseDTO> addComment(@PathVariable UUID videoId,
                                                  @RequestBody @Valid String text) {
        commentsService.addComment(videoId, text);

        ResponseDTO responseDTO = new ResponseDTO(
                CommentConstants.StatusCode.CREATED.getCode(),
                CommentConstants.COMMENT_ADDED
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDTO);
    }


    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByVideo(@PathVariable UUID videoId) {
        List<CommentDTO> comments = commentsService.getCommentsByVideo(videoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }


    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable UUID commentId) {
        commentsService.deleteComment(commentId);

        ResponseDTO responseDTO = new ResponseDTO(
                CommentConstants.StatusCode.OK.getCode(),
                CommentConstants.COMMENT_DELETED
        );
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(responseDTO);
    }


    @PostMapping("/reply/{parentCommentId}")
    public ResponseEntity<CommentDTO> replyToComment(@PathVariable UUID parentCommentId,
                                                     @RequestBody @Valid String text) {
        CommentDTO replyComment = commentsService.replyToComment(parentCommentId, text);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(replyComment);
    }
}
