package com.odop.community.service.post;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void add(PostDTO postDTO) throws IOException;
    List<PostDTO> findAll() throws IOException;
    PostDetailDTO findById(Long id) throws IOException;
    void modify(PostDTO postDTO) throws IOException;
    void remove(Long id);

    void addComment(CommentDTO commentDTO);
    void modifyComment(CommentDTO commentDTO);
    void removeComment(Long id);
}
