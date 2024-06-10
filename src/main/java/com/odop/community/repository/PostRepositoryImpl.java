package com.odop.community.repository;

import com.odop.community.domain.dto.PostDTO;
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

import java.time.LocalDateTime;
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
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Post> selectAll() {
        try {
            return jpaQueryFactory.select(qPost)
                    .from(qPost)
                    .where(qPost.deletedAt.isNull())
                    .orderBy(qPost.createdAt.desc())
                    .fetch();
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing select query", e);
        }
    }

    @Override
    public Post selectById(Post post) {
        try {
            jpaQueryFactory.update(qPost)
                    .set(qPost.viewCount, qPost.viewCount.add(1))
                    .where(qPost.id.eq(post.getId()).and(qPost.deletedAt.isNull()))
                    .execute();


            return jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.id.eq(post.getId()).and(qPost.deletedAt.isNull()))
                    .fetchOne();

        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing select query", e);
        }
    }

    @Override
    public void update(Post post) {
        try {
            jpaQueryFactory.update(qPost)
                    .where(qPost.id.eq(post.getId()))
                    .set(qPost.title, post.getTitle())
                    .set(qPost.content, post.getContent())
                    .set(qPost.image, post.getImage())
                    .set(qPost.imageName, post.getImageName())
                    .execute();

        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing update query", e);
        }
    }

    @Override
    public void delete(Post post) {
        try {
            jpaQueryFactory.update(qPost)
                    .where(qPost.id.eq(post.getId()))
                    .set(qPost.deletedAt, LocalDateTime.now())
                    .execute();
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing delete query", e);
        }
    }

    @Override
    public void insertComment(Comment comment) {
        try {
            entityManager.persist(comment);
        } catch(DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing insert query", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Comment> selectAllComments(PostDTO postDTO) {
        try {
             return jpaQueryFactory.selectFrom(qComment)
                    .where(qComment.postId.eq(postDTO.getId()).and(qComment.deletedAt.isNull()))
                    .orderBy(qComment.createdAt.desc())
                    .fetch();

        } catch(DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing select query", e);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        try {
            jpaQueryFactory.update(qComment)
                    .where(qComment.id.eq(comment.getId()))
                    .set(qComment.content, comment.getContent())
                    .execute();

        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing update query", e);
        }
    }

    @Override
    public void deleteComment(Comment comment) {
        try {
            jpaQueryFactory.update(qComment)
                    .where(qComment.id.eq(comment.getId()))
                    .set(qComment.deletedAt, LocalDateTime.now())
                    .execute();
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing delete query", e);
        }
    }
}
