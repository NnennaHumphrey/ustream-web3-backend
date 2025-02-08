package com.ustream_web3.services;

import com.ustream_web3.dtos.CommentDTO;

import java.util.List;
import java.util.UUID;

public interface CommentsService {

    CommentDTO addComment(UUID videoId, String text);

    List<CommentDTO> getCommentsByVideo(UUID videoId);

    void deleteComment(UUID commentId);

    CommentDTO replyToComment(UUID parentCommentId, String text);
}
