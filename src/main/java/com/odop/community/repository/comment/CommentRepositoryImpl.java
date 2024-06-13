package com.odop.community.repository.comment;

import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
