package com.odop.community.repository.comment;

import com.odop.community.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findAllAndDeletedAtIsNull(Long postId);
    void update(Comment comment);
    void softDeleteById(Long id);
}
