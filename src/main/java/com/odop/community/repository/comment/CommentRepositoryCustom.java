package com.odop.community.repository.comment;

import com.odop.community.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> findAllAndDeletedAtIsNull(Long postId);
}
