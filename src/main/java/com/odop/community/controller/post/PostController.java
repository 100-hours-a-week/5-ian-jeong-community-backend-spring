package com.odop.community.controller.post;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import org.springframework.http.ResponseEntity;


public interface PostController {
    ResponseEntity<?> add(PostDTO postDTO);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> modify(Long id, PostDTO postDTO);
    ResponseEntity<?> remove(Long id);

    ResponseEntity<?> addComment(Long postId, CommentDTO commentDTO);
    ResponseEntity<?> modifyComment(Long postId, Long id, CommentDTO commentDTO);
    ResponseEntity<?> removeComment(Long id);
}
