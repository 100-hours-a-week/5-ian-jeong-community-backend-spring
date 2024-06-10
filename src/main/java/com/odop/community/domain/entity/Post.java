package com.odop.community.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    public Post() {}

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
        viewCount = 0L;
        likeCount = 0L;
        commentCount = 0L;
    }

    public Post(
            Long id,
            Long userId,
            String title,
            String content,
            String imageName,
            String image,
            Long viewCount,
            Long likeCount,
            Long commentCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.image = image;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
