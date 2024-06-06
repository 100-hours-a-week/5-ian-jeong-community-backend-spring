package com.odop.community.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Post {
    private long id;
    private long userId;
    private String title;
    private String content;
    private String imageName;
    private String image; // 경로
}
