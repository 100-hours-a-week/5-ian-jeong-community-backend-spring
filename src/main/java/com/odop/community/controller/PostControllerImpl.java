package com.odop.community.controller;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.domain.ResponseData;
import com.odop.community.domain.entity.Post;
import com.odop.community.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public ResponseEntity<ResponseData<List<Post>>> findAll() {
        try {
            PostsDTO postsDTO = postService.findAll();
            if (postsDTO.getList().size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            ResponseData<List<Post>> responseData = new ResponseData<>(postsDTO.getList());

            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch(DataAccessResourceFailureException e) {
            log.error("Error fetching posts = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("Error loading image of post = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseData<PostDetailDTO>> findById(@PathVariable("postId") Long id) {
        PostDTO postDTO = new PostDTO(id, null, null, null, null, null, null);
        try {
            PostDetailDTO postDetailDTO = postService.findById(postDTO);
            ResponseData<PostDetailDTO> responseData = new ResponseData<>(postDetailDTO);

            return new ResponseEntity<>(responseData, HttpStatus.OK);

        } catch(RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> modify(@PathVariable("postId") Long id, @RequestBody PostDTO postDTO) {
        postDTO.setId(id);
        try {
            postService.modify(postDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch(EmptyResultDataAccessException e) {
            log.error("Error attempting to find a post by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to find a post by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IOException e) {
            log.error("Error attempting to save image = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> remove(@PathVariable("postId") Long id) {
        try {
            PostDTO postDTO = new PostDTO(id, null, null, null, null, null, null);
            postService.remove(postDTO);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to delete a post by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> addComment(@PathVariable("postId") Long postId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setPostId(postId);
        try {
            postService.addComment(commentDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to add a comment = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> modifyComment(Long id, String text) {
        // pathvariable 어노테이션 ㄱㄱ

        return null;
    }

    @Override
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(Long id) {
        // pathvariable 어노테이션 ㄱㄱ
        return null;
    }
}
