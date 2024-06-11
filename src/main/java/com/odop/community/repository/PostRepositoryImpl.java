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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        } catch(RuntimeException e) {
            throw new RuntimeException("Query to insert new post failed => [" + post.getTitle() + "]", e);
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

        } catch (RuntimeException e) {
            throw new RuntimeException("Query to select posts failed", e);
        }
    }

    @Override
    public Optional<Post> selectById(Post post) {
        try {
            jpaQueryFactory.update(qPost)
                    .set(qPost.viewCount, qPost.viewCount.add(1))
                    .where(qPost.id.eq(post.getId()).and(qPost.deletedAt.isNull()))
                    .execute();

            return Optional.ofNullable(jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.id.eq(post.getId()).and(qPost.deletedAt.isNull()))
                    .fetchOne()
            );

        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Post with id not found => [" + post.getId() + "]", 1, e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to select a post", e);
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

        } catch (RuntimeException e) {
            throw new RuntimeException("Error executing update query", e);
        }
    }

    @Override
    public void delete(Post post) {
        try {
            jpaQueryFactory.update(qPost)
                    .where(qPost.id.eq(post.getId()))
                    .set(qPost.deletedAt, LocalDateTime.now())
                    .execute();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error executing delete query", e);
        }
    }

    @Override
    public void insertComment(Comment comment) {
        try {
            entityManager.persist(comment);
        } catch(RuntimeException e) {
            throw new RuntimeException("Error executing insert query", e);
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

        } catch(RuntimeException e) {
            throw new RuntimeException("Error executing select query", e);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        try {
            jpaQueryFactory.update(qComment)
                    .where(qComment.id.eq(comment.getId()))
                    .set(qComment.content, comment.getContent())
                    .execute();

        } catch (RuntimeException e) {
            throw new RuntimeException("Error executing update query", e);
        }
    }

    @Override
    public void deleteComment(Comment comment) {
        try {
            jpaQueryFactory.update(qComment)
                    .where(qComment.id.eq(comment.getId()))
                    .set(qComment.deletedAt, LocalDateTime.now())
                    .execute();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error executing delete query", e);
        }
    }
}
