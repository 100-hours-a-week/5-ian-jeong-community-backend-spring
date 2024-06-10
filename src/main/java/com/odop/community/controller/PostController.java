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
    ResponseEntity<Void> modify(PostDTO postDTO);
    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Void> addComment(CommentDTO commentDTO);
    ResponseEntity<Void> modifyComment(Long id, String text);
    ResponseEntity<Void> deleteComment(Long id);
}
