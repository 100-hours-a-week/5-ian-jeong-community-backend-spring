package com.odop.community.service;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;

import java.io.IOException;

public interface PostService {
    void add(PostDTO postDTO) throws IOException;
    PostsDTO findAll();
    PostDTO findById();
    void modify();
    void delete();

    void addComment(CommentDTO commentDTO);
    void updateComment(CommentDTO commentDTO);
    void deleteComment(Long id);
}
