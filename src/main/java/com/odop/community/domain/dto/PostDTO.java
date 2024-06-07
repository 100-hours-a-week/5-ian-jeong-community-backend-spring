package com.odop.community.domain.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String imageName;
    private String image; // 경로
    private Long viewCount;
    private Long likeCount;
    private Long CommentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
