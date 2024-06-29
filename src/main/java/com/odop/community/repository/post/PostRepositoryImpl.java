package com.odop.community.repository.post;

import com.odop.community.domain.entity.Post;
import com.odop.community.domain.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.odop.community.domain.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost qPost = post;

    @Override
    public void incrementViewCount(Long id) {
        try {
            jpaQueryFactory.update(qPost)
                    .set(qPost.viewCount, qPost.viewCount.add(1))
                    .where(qPost.id.eq(id).and(qPost.deletedAt.isNull()))
                    .execute();
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to update a post failed", e);
        }
    }

    @Override
    public Optional<Post> findByIdAndDeletedAtIsNull(Long id) {
        try {
            return Optional.ofNullable(
                    jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.id.eq(id).and(qPost.deletedAt.isNull()))
                    .fetchOne()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Post with id not found => [" + id + "]", 1, e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to select a post failed", e);
        }
    }

    @Override
    public void update(Post post) {
        try {
            jpaQueryFactory.update(qPost)
                    .set(qPost.title, post.getTitle())
                    .set(qPost.content, post.getContent())
                    .set(qPost.imageName, post.getImageName())
                    .set(qPost.image, post.getImage())
                    .where(qPost.id.eq(post.getId()))
                    .execute();
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to update a post failed", e);
        }
    }

    @Override
    public void softDeleteById(Long id) {
        try {
            jpaQueryFactory.update(qPost)
                    .set(qPost.deletedAt, LocalDateTime.now())
                    .where(qPost.id.eq(id))
                    .execute();
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to delete a post failed", e);
        }
    }
}
