package com.odop.community.service.post;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;
import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;
import com.odop.community.repository.comment.CommentRepository;
import com.odop.community.repository.post.PostRepository;
import com.odop.community.service.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Value("${post.image.directory}")
    private String POST_IMAGE_DIRECTORY;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Uploader s3Uploader;

    @Override
    public void add(PostDTO postDTO, MultipartFile multipartFile) throws IOException {
        try {
            String imagePath = "";

            if (multipartFile != null && !multipartFile.isEmpty()) {
                imagePath = s3Uploader.upload(multipartFile, POST_IMAGE_DIRECTORY);
            }

            postDTO.setImage(imagePath);
            postRepository.save(postDTO.convertToEntity());
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to insert post failed", e);
        }
    }

    @Override
    public List<PostDTO> findAll() throws IOException {
        try {
            return convertToPostDTOList(postRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc());
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to select posts failed", e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PostDetailDTO findById(Long id) throws IOException {
        postRepository.incrementViewCount(id);

        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new EmptyResultDataAccessException("Post with id not found", 1, new Exception())
                );

        PostDTO postDTO = PostDTO.convertToDTO(post);
        List<CommentDTO> commentsDTO = convertToCommentDTOList(commentRepository.findAllAndDeletedAtIsNull(id));

        return new PostDetailDTO(postDTO, commentsDTO);
    }



    @Override
    public void modify(PostDTO postDTO, MultipartFile multipartFile) throws IOException {
        Post post = postRepository.findById(postDTO.getId())
                .orElseThrow(() ->
                        new EmptyResultDataAccessException("Post with id not found => [" + postDTO.getId() + "]", 1, new Exception())
                );

        String imagePath = "";

        // 기존이미지가 있다면 삭제 필요
        if(!post.getImage().isEmpty() && multipartFile != null && !multipartFile.isEmpty()) {
            imagePath = s3Uploader.updateFile(multipartFile, post.getImage(), POST_IMAGE_DIRECTORY);
        }

        // 기존이미지가 없고 업데이트 이미지가 있다면
        if(post.getImage().isEmpty() && multipartFile != null && !multipartFile.isEmpty()) {
            imagePath = s3Uploader.upload(multipartFile, POST_IMAGE_DIRECTORY);
        }

        postDTO.setImage(imagePath);

        try {
            postRepository.update(postDTO.convertToEntity());
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to update post failed", e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            postRepository.softDeleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to delete post failed", e);
        }
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        try {
            commentRepository.save(commentDTO.convertToEntity());
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to insert comment failed", e);
        }
    }

    @Override
    public void modifyComment(CommentDTO commentDTO) {
        try {
            commentRepository.update(commentDTO.convertToEntity());
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to update comment failed", e);
        }
    }

    @Override
    public void removeComment(Long id) {
        try {
            commentRepository.softDeleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to delete comment failed", e);
        }
    }



    private List<PostDTO> convertToPostDTOList(List<Post> posts) throws IOException {
        List<PostDTO> postsDTO = new ArrayList<>();

        for (Post post : posts) {
            postsDTO.add(PostDTO.convertToDTO(post));
        }

        return postsDTO;
    }

    private List<CommentDTO> convertToCommentDTOList(List<Comment> comments) {
        List<CommentDTO> commentsDTO = new ArrayList<>();

        for (Comment comment : comments) {
            commentsDTO.add(CommentDTO.convertToCommentDTO(comment));
        }
        return commentsDTO;
    }
}
