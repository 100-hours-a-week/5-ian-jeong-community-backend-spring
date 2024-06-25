package com.odop.community.repository.comment;

import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QComment qComment = QComment.comment;

    @Override
    public List<Comment> findAllAndDeletedAtIsNull(Long postId) {
        try {
            return jpaQueryFactory.selectFrom(qComment)
                    .where(qComment.postId.eq(postId).and(qComment.deletedAt.isNull()))
                    .orderBy(qComment.createdAt.desc())
                    .fetch();

        } catch(RuntimeException e) {
            throw new RuntimeException("Query to select comment failed", e);
        }
    }

    @Override
    public void update(Comment comment) {
        try {
            jpaQueryFactory.update(qComment)
                    .set(qComment.content, comment.getContent())
                    .where(qComment.id.eq(comment.getId()))
                    .execute();
        } catch(RuntimeException e) {
            throw new RuntimeException("Query to update comment failed", e);
        }
    }

    @Override
    public void softDeleteById(Long id) {
        try {
            jpaQueryFactory.update(qComment)
                    .set(qComment.deletedAt, LocalDateTime.now())
                    .where(qComment.id.eq(id))
                    .execute();
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to delete a comment failed", e);
        }
    }
}
