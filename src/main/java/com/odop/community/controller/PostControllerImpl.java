package com.odop.community.controller;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.domain.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {


    @Override
    public ResponseEntity<Void> add(PostDTO postDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseMessage<PostsDTO>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseMessage<PostDTO>> getById(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(PostDTO postDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addComment(CommentDTO commentDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateComment(long id, String text) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteComment(long id) {
        return null;
    }
}
