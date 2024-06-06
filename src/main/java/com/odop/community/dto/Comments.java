package com.odop.community.dto;

import lombok.AllArgsConstructor;

import java.util.List;

public class Comments {
    private final List<Comment> comments;

    public Comments(List<Comment> comments) {
        this.comments = comments;
    }
}
