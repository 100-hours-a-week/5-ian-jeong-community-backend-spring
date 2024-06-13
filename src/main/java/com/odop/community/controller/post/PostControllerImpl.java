package com.odop.community.controller.post;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.odop.community.constant.ErrorMessage.*;
import static com.odop.community.response.ResponseHandler.handleException;
import static com.odop.community.response.ResponseHandler.handleResponse;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostControllerImpl implements PostController {
    private final PostService postService;

    @Override
    @PostMapping
    public ResponseEntity<?> add(@RequestBody PostDTO postDTO) {
        try {
            postService.add(postDTO);
            return handleResponse(HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_ADD_POST, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return handleException(e, ERROR_STORE_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            return handleResponse(postService.findAll(), HttpStatus.OK);

        } catch(RuntimeException e) {
            return handleException(e, ERROR_FIND_POST, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return handleException(e, ERROR_LOAD_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<?> findById(@PathVariable("postId") Long id) {
        try {
            return handleResponse(postService.findById(id), HttpStatus.OK);

        } catch(EmptyResultDataAccessException e) {
            return handleException(e, ERROR_FIND_POST, HttpStatus.NOT_FOUND);
        } catch(RuntimeException e) {
            return handleException(e, ERROR_FIND_POST, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return handleException(e, ERROR_LOAD_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<?> modify(@PathVariable("postId") Long id, @RequestBody PostDTO postDTO) {
        try {
            postDTO.setId(id);
            postService.modify(postDTO);
            return handleResponse(HttpStatus.NO_CONTENT);

        }  catch(EmptyResultDataAccessException e) {
            return handleException(e, ERROR_FIND_POST, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return handleException(e, ERROR_MODIFY_POST, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IOException e) {
            return handleException(e, ERROR_STORE_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> remove(@PathVariable("postId") Long id) {
        try {
            postService.remove(id);
            return handleResponse(HttpStatus.NO_CONTENT);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_DELETE_POST, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentDTO commentDTO
    ) {
        try {
            commentDTO.setPostId(postId);
            postService.addComment(commentDTO);
            return handleResponse(HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_ADD_COMMENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> modifyComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long id,
            @RequestBody CommentDTO commentDTO
    ) {
        try {
            commentDTO.setId(id);
            commentDTO.setPostId(postId);
            postService.modifyComment(commentDTO);
            return handleResponse(HttpStatus.NO_CONTENT);

        } catch(EmptyResultDataAccessException e) {
            return handleException(e, ERROR_FIND_COMMENT, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return handleException(e, ERROR_MODIFY_COMMENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> removeComment(@PathVariable("commentId") Long id) {
        try {
            postService.removeComment(id);
            return handleResponse(HttpStatus.NO_CONTENT);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_DELETE_COMMENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
