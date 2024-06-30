package com.odop.community.controller.post;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface PostController {
    ResponseEntity<?> add(PostDTO postDTO, MultipartFile multipartFile);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> modify(Long id, PostDTO postDTO, MultipartFile multipartFile);
    ResponseEntity<?> remove(Long id);

    ResponseEntity<?> addComment(Long postId, CommentDTO commentDTO);
    ResponseEntity<?> modifyComment(Long postId, Long id, CommentDTO commentDTO);
    ResponseEntity<?> removeComment(Long id);
}
