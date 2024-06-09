package com.odop.community.controller;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.domain.ResponseMessage;
import com.odop.community.domain.entity.Post;
import com.odop.community.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {
    private final PostService postService;

    @Override
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody PostDTO postDTO) {

        try {
            postService.add(postDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to add a post = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("Error attempting to save image = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<ResponseMessage<List<Post>>> findAll() {
        try {
            PostsDTO postsDTO = postService.findAll();
            ResponseMessage<List<Post>> responseMessage = new ResponseMessage<>(postsDTO.getList());

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        } catch(DataAccessResourceFailureException e) {
            log.error("Error fetching posts = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseMessage<PostDTO>> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> modify(PostDTO postDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addComment(CommentDTO commentDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateComment(Long id, String text) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long id) {
        return null;
    }
}
