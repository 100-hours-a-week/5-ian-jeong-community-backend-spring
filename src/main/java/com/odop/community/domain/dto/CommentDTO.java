package com.odop.community.domain.dto;

import com.odop.community.domain.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CommentDTO {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public CommentDTO() {}

    public CommentDTO(Long id) {
        this.id = id;
    }

    public Comment convertToEntity() {
        return new Comment(id, postId, userId, content);
    }
}
