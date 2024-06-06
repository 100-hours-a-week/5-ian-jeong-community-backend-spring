package com.odop.community.controller;

import com.odop.community.dto.Comment;
import com.odop.community.dto.Post;
import com.odop.community.dto.Posts;
import com.odop.community.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;

public class PostControllerImpl implements PostController{

    @Override
    public ResponseEntity<Void> add(Post post) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseMessage<Posts>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseMessage<Post>> getById(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> update(Post post) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addComment(Comment comment) {
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
