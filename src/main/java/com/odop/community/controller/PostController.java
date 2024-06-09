package com.odop.community.controller;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.domain.ResponseMessage;
import com.odop.community.domain.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PostController {
    ResponseEntity<Void> add(PostDTO postDTO);
    ResponseEntity<ResponseMessage<List<Post>>> findAll();
    ResponseEntity<ResponseMessage<PostDTO>> findById(Long id);
    ResponseEntity<Void> modify(PostDTO postDTO);
    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Void> addComment(CommentDTO commentDTO);
    ResponseEntity<Void> updateComment(Long id, String text);
    ResponseEntity<Void> deleteComment(Long id);
}
