package com.odop.community.repository;

import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;

import java.util.List;

public interface PostRepository {
    void insert(Post post);
    List<Post> selectAll();
    Post selectById(Post post);
    void update(Post Post);
    void delete(Post post);


    void insertComment(Comment comment);
    List<Comment> selectAllComments(PostDTO postDTO);
    void updateComment(Comment comment);
    void deleteComment(Comment comment);
}
