package com.odop.community.controller;

import com.odop.community.dto.Comment;
import com.odop.community.dto.Post;
import com.odop.community.dto.Posts;
import com.odop.community.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;


public interface PostController {
    ResponseEntity<Void> add(Post post);
    ResponseEntity<ResponseMessage<Posts>> getAll();
    ResponseEntity<ResponseMessage<Post>> getById(long id);
    ResponseEntity<Void> update(Post post);
    ResponseEntity<Void> delete(long id);

    ResponseEntity<Void> addComment(Comment comment);
    ResponseEntity<Void> updateComment(long id, String text);
    ResponseEntity<Void> deleteComment(long id);
}
