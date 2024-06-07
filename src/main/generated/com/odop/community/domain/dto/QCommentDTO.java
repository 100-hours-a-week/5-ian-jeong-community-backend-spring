package com.odop.community.domain.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommentDTO is a Querydsl query type for CommentDTO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentDTO extends EntityPathBase<CommentDTO> {

    private static final long serialVersionUID = 832482697L;

    public static final QCommentDTO commentDTO = new QCommentDTO("commentDTO");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCommentDTO(String variable) {
        super(CommentDTO.class, forVariable(variable));
    }

    public QCommentDTO(Path<? extends CommentDTO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommentDTO(PathMetadata metadata) {
        super(CommentDTO.class, metadata);
    }

}

