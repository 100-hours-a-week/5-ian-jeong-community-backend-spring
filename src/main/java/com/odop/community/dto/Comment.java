package com.odop.community.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Comment {
    private long id;
    private long postId;
    private long userId;
    private String text;
}
