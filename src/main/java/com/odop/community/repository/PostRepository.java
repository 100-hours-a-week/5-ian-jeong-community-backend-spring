package com.odop.community.repository;

import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;

import java.util.List;

public interface PostRepository {
    void insert(Post post);
    List<Post> selectAll();
    Post selectById(Long postId);
    void update(Post Post);
    void delete(Long postId);

    void voidInsertComment(Comment comment);
    void updateComment(Long commentId);
    void deleteComment(Long commentId);
}
