package com.odop.community.repository;

import com.odop.community.dto.Comment;
import com.odop.community.dto.Post;
import com.odop.community.dto.User;

import java.util.List;

public interface PostRepository {
    void insert(Post post);
    List<Post> selectAll();
    User selectById(long postId);
    void update(Post post);
    void delete(long postId);

    void voidInsertComment(Comment comment);
    void updateComment(long commentId);
    void deleteComment(long commentId);
}
