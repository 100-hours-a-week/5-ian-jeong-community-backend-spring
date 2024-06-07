package com.odop.community.service;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public void add(PostDTO postDTO) {

    }

    @Override
    public PostsDTO getAll() {
        return null;
    }

    @Override

    public PostDTO getById() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void addComment(CommentDTO commentDTO) {

    }

    @Override
    public void updateComment(CommentDTO commentDTO) {

    }

    @Override
    public void deleteComment(long id) {

    }
}
