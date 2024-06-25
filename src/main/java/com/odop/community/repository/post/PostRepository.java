package com.odop.community.repository.post;

import com.odop.community.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findAllByDeletedAtIsNullOrderByCreatedAtDesc();
}
