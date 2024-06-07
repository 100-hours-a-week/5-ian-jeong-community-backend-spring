package com.odop.community.domain.dto;

import java.util.List;

public class PostsDTO {
    private final List<PostDTO> postsDTO;

    public PostsDTO(List<PostDTO> postsDTO) {
        this.postsDTO = postsDTO;
    }
}
