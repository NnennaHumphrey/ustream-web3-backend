package com.ustream_web3.repositories;

import com.ustream_web3.dtos.CommentDTO;
import com.ustream_web3.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentsRepository extends JpaRepository<Comments, UUID> {

    List<Comments> findByVideoId(UUID videoId);

    List<CommentDTO> findByParentCommentId(UUID parentCommentId);
}
