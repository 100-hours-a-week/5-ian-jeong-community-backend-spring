package com.odop.community.domain.dto;

import com.odop.community.domain.entity.Post;

import java.util.List;

public class PostsDTO {
    private final List<Post> postsDTO;

    public PostsDTO(List<Post> posts) {
        this.postsDTO = posts;
    }

    public List<Post> getList() {
        return postsDTO;
    }

}
