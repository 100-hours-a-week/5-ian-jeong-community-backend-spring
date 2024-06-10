package com.odop.community.domain.dto;

import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PostDetailDTO {
    private final Post post;
    private final List<Comment> comments;
}
