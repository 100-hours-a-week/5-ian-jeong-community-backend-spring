package com.odop.community.repository.comment;

import com.odop.community.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    @Query("UPDATE Comments c SET c.deleted_at = CURRENT_TIMESTAMP WHERE c.id = :id")
    void softDeleteById(@Param("id") Long id);
}
