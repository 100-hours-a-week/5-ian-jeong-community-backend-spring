package com.odop.community.service;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;

public interface PostService {
    void add(PostDTO postDTO);
    PostsDTO getAll();
    PostDTO getById();
    void update();
    void delete();

    void addComment(CommentDTO commentDTO);
    void updateComment(CommentDTO commentDTO);
    void deleteComment(long id);
}
