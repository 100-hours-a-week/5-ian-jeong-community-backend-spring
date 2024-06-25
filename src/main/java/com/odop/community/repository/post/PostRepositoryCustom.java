package com.odop.community.repository.post;

import com.odop.community.domain.entity.Post;

import java.util.Optional;

public interface PostRepositoryCustom {
    void incrementViewCount(Long id);
    Optional<Post> findByIdAndDeletedAtIsNull(Long id);
    void update(Post post);
    void softDeleteById(Long id);
}
