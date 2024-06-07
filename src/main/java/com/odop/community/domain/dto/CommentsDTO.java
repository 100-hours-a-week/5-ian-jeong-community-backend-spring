package com.odop.community.domain.dto;

import java.util.List;

public class CommentsDTO {
    private final List<CommentDTO> commentDTOS;

    public CommentsDTO(List<CommentDTO> commentDTOS) {
        this.commentDTOS = commentDTOS;
    }
}
