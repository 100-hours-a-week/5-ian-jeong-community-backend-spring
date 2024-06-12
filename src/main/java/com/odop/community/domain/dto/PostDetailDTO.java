package com.odop.community.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PostDetailDTO {
    private final PostDTO postDTO;
    private final List<CommentDTO> commentsDTO;
}
