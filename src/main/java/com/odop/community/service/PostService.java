package com.odop.community.service;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;
import com.odop.community.domain.dto.PostsDTO;

import java.io.IOException;

public interface PostService {
    void add(PostDTO postDTO) throws IOException;
    PostsDTO findAll() throws IOException;
    PostDetailDTO findById(PostDTO postDTO);
    void modify(PostDTO postDTO) throws IOException;
    void remove(PostDTO postDTO);

    void addComment(CommentDTO commentDTO);
    void modifyComment(CommentDTO commentDTO);
    void removeComment(CommentDTO commentDTO);
}
