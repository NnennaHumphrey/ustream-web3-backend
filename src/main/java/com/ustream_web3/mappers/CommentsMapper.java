package com.ustream_web3.mappers;

import com.ustream_web3.dtos.CommentDTO;
import com.ustream_web3.entities.Comments;
import com.ustream_web3.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CommentsMapper {

    public CommentDTO toDTO(Comments comments) {
        if (comments == null || comments.getUser() == null) {
            return null;
        }

        return CommentDTO.builder()
                .username(comments.getUser().getUsername())
                .text(comments.getText())
                .build();
    }

    public Comments toEntity(CommentDTO commentDTO, User user) {
        if (commentDTO == null || user == null) {
            return null;
        }

        Comments comments = new Comments();
        comments.setUser(user);
        comments.setText(commentDTO.getText());

        return comments;
    }
}
