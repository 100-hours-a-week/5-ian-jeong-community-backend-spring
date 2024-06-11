package com.odop.community.domain.dto;

import com.odop.community.domain.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String imageName;
    private String image;
    private LocalDateTime createdAt;

    public PostDTO() {}

    public PostDTO(Long id) {
        this.id = id;
    }

    public PostDTO(
            Long id,
            Long userId,
            String title,
            String content,
            String imageName,
            String image,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.image = image;
        this.createdAt = createdAt;
    }

    public Post convertToPostEntity() {
        return new Post(id, userId, title, content, imageName, image);
    }
}
