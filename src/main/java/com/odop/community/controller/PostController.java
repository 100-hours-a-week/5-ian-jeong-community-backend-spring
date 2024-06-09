package com.odop.community.controller;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.domain.ResponseMessage;
import org.springframework.http.ResponseEntity;


public interface PostController {
    ResponseEntity<Void> add(PostDTO postDTO);
    ResponseEntity<ResponseMessage<PostsDTO>> getAll();
    ResponseEntity<ResponseMessage<PostDTO>> getById(Long id);
    ResponseEntity<Void> modify(PostDTO postDTO);
    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Void> addComment(CommentDTO commentDTO);
    ResponseEntity<Void> updateComment(Long id, String text);
    ResponseEntity<Void> deleteComment(Long id);
}
