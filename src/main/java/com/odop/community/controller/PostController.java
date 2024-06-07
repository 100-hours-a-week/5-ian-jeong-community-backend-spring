package com.odop.community.controller;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.domain.ResponseMessage;
import org.springframework.http.ResponseEntity;


public interface PostController {
    ResponseEntity<Void> add(PostDTO postDTO);
    ResponseEntity<ResponseMessage<PostsDTO>> getAll();
    ResponseEntity<ResponseMessage<PostDTO>> getById(long id);
    ResponseEntity<Void> update(PostDTO postDTO);
    ResponseEntity<Void> delete(long id);

    ResponseEntity<Void> addComment(CommentDTO commentDTO);
    ResponseEntity<Void> updateComment(long id, String text);
    ResponseEntity<Void> deleteComment(long id);
}
