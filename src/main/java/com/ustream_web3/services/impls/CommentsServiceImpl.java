package com.ustream_web3.services.impls;

import com.ustream_web3.dtos.CommentDTO;
import com.ustream_web3.entities.Comments;
import com.ustream_web3.entities.User;
import com.ustream_web3.entities.Videos;
import com.ustream_web3.exceptions.ResourceNotFoundException;
import com.ustream_web3.exceptions.UnauthorizedOperationException;
import com.ustream_web3.mappers.CommentsMapper;
import com.ustream_web3.repositories.CommentsRepository;
import com.ustream_web3.repositories.UserRepository;
import com.ustream_web3.repositories.VideosRepository;
import com.ustream_web3.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private VideosRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public CommentDTO addComment(UUID videoId, String text) {
        User user = getAuthenticatedUser();

        Videos video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found with ID: " + videoId));


        Comments comment = new Comments();
        comment.setVideo(video);
        comment.setText(text);
        comment.setUser(user);

        Comments savedComment = commentsRepository.save(comment);


        return commentsMapper.toDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByVideo(UUID videoId) {
        List<Comments> comments = commentsRepository.findByVideoId(videoId);
        if (comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments found for video with ID: " + videoId);
        }


        return comments.stream()
                .map(commentsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(UUID commentId) {
        User currentUser = getAuthenticatedUser();
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));

        if (!comment.getUser().equals(currentUser) && !comment.getVideo().getUser().equals(currentUser)) {
            throw new UnauthorizedOperationException("You do not have permission to delete this comment");
        }

        commentsRepository.deleteById(commentId);
    }

    @Override
    public CommentDTO replyToComment(UUID parentCommentId, String text) {
        User user = getAuthenticatedUser();

        Comments parentComment = commentsRepository.findById(parentCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found with ID: " + parentCommentId));

        Comments replyComment = new Comments();
        replyComment.setText(text);
        replyComment.setUser(user);
        replyComment.setParentComment(parentComment);
        replyComment.setVideo(parentComment.getVideo());

        Comments savedReplyComment = commentsRepository.save(replyComment);

        return commentsMapper.toDTO(savedReplyComment);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
}
