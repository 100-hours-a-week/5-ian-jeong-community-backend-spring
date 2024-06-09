package com.odop.community.domain.dto;


import com.odop.community.domain.entity.Post;
import com.odop.community.domain.entity.User;
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
    private LocalDateTime created_at;

    public PostDTO() {}

    public PostDTO(
            Long userId,
            String title,
            String content,
            String imageName,
            String image
    ) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.image = image;
    }

    public Post convertToPostEntity() {
        return new Post(userId, title, content, imageName, image);
    }
}
