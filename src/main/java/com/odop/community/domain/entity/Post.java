package com.odop.community.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String image;

    @Column(name="image_name")
    private String imageName;

    @Column(name="view_count" ,nullable = false)
    private Long viewCount;

    @Column(name="like_count", nullable = false)
    private Long likeCount;

    @Column(name="comment_count", nullable = false)
    private Long commentCount;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Post(
            Long id,
            Long userId,
            String title,
            String content,
            String imageName,
            String image
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.image = image;
    }
}
