package com.odop.community.repository;

import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;
import com.odop.community.domain.entity.QComment;
import com.odop.community.domain.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost qPost = QPost.post;
    private final QComment qComment = QComment.comment;

    @Override
    public void insert(Post post) {
        try {
            entityManager.persist(post);
        } catch(DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing insert query", e);
        }
    }

    @Override
    public List<Post> selectAll() {
//        return jpaQueryFactory.select(qPost)
//                .from(qPost)
//                .where(qPost.deletedAt.isNull())
//                .orderBy(qPost.createdAt.desc())
//                .fetch();
        return null;
    }

    @Override
    public Post selectById(Long postId) {
        jpaQueryFactory.update(qPost)
                .set(qPost.viewCount, qPost.viewCount.add(1))
                .where(qPost.id.eq(postId).and(qPost.deletedAt.isNull()))
                .execute();

        Post post = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.id.eq(postId).and(qPost.deletedAt.isNull()))
                .fetchOne();

        if (post == null) {
            throw new RuntimeException("Post not found");
        }

        List<Comment> comments = jpaQueryFactory.selectFrom(qComment)
                .where(qComment.postId.eq(postId).and(qComment.deletedAt.isNull()))
                .orderBy(qComment.createdAt.desc())
                .fetch();


        // DTO 와 엔티티 분리 (디비관리 모양새와 다르기때문)
        // 격리수준세팅


        return null;
    }

    @Override
    public void update(Post post) {

    }

    @Override
    public void delete(Long postId) {

    }

    @Override
    public void voidInsertComment(Comment comment) {

    }

    @Override
    public void updateComment(Long commentId) {

    }

    @Override
    public void deleteComment(Long commentId) {

    }
}
