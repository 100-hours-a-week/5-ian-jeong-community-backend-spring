package com.odop.community.controller;

import com.odop.community.domain.ResponseData;
import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;
import com.odop.community.domain.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PostController {
    ResponseEntity<Void> add(PostDTO postDTO);
    ResponseEntity<ResponseData<List<Post>>> findAll();
    ResponseEntity<ResponseData<PostDetailDTO>> findById(Long id);
    ResponseEntity<Void> modify(Long id, PostDTO postDTO);
    ResponseEntity<Void> remove(Long id);

    ResponseEntity<Void> addComment(Long postId, CommentDTO commentDTO);
    ResponseEntity<Void> modifyComment(Long postId, Long id, CommentDTO commentDTO);
    ResponseEntity<Void> removeComment(Long id);
}
