package com.odop.community.domain.dto;

import com.odop.community.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String imageName;
    private String image;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;
    private LocalDateTime createdAt;

    public Post convertToEntity() {
        return new Post(id, userId, title, content, imageName, image);
    }

    public static PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUserId(),
                post.getTitle(),
                post.getContent(),
                post.getImageName(),
                post.getImage(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt()
        );
    }
}
