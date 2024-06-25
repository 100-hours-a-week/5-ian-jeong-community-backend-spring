package com.odop.community.repository.post;

import com.odop.community.domain.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findAllByDeletedAtIsNull(Sort sort);
    @Query("UPDATE Posts p SET p.deleted_at = CURRENT_TIMESTAMP WHERE p.id = :id")
    void softDeleteById(@Param("id") Long id);
}
