package com.odop.community.repository;

import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;

import java.util.List;

public interface PostRepository {
    void insert(Post post);
    List<Post> selectAll();
    Post selectById(long postId);
    void update(Post Post);
    void delete(long postId);

    void voidInsertComment(Comment comment);
    void updateComment(long commentId);
    void deleteComment(long commentId);
}
