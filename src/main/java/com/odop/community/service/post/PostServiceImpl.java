package com.odop.community.service.post;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;
import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;
import com.odop.community.repository.comment.CommentRepository;
import com.odop.community.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

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
    private static final String POST_IMAGE_DIRECTORY = "/var/image/";

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public void add(PostDTO postDTO) throws IOException {
        try {
            storePostImage(postDTO);
            postRepository.save(postDTO.convertToEntity());
        } catch (RuntimeException e) {
            throw new RuntimeException("Query to insert post failed", e);
        }
    }

    @Override
    public List<PostDTO> findAll() throws IOException {
        try {
            return convertToPostDTOList(postRepository.findAllByDeletedAtIsNull(Sort.by(Sort.Direction.DESC, "createdAt")));
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
        loadPostImage(postDTO);
        List<CommentDTO> commentsDTO = convertToCommentDTOList(commentRepository.findAllAndDeletedAtIsNull(id));

        return new PostDetailDTO(postDTO, commentsDTO);
    }



    @Override
    public void modify(PostDTO postDTO) throws IOException {
        Post post = postRepository.findById(postDTO.getId())
                .orElseThrow(() ->
                        new EmptyResultDataAccessException("Post with id not found => [" + postDTO.getId() + "]", 1, new Exception())
                );

        updatePostImage(postDTO, post);
        if (postDTO.getImage().isEmpty()) {
            postDTO.setImage(post.getImage());
        }

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





    private void storePostImage(PostDTO postDTO) throws IOException {

        if (!postDTO.getImage().isEmpty()) {
            Path imageDirPath = Paths.get(POST_IMAGE_DIRECTORY);
            // 디렉터리가 존재하지 않으면 생성
            if (!Files.exists(imageDirPath)) {
                Files.createDirectories(imageDirPath);
            }

            String imageName = UUID.randomUUID().toString();
            Path imagePath = Paths.get(POST_IMAGE_DIRECTORY + imageName);

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(postDTO.getImage().getBytes(), outputStream);
                postDTO.setImage(imageName);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }

    private void loadPostImage(PostDTO postDTO) throws IOException {
        if (!postDTO.getImage().isEmpty()) {
            Path imagePath = Paths.get(POST_IMAGE_DIRECTORY + postDTO.getImage());
            String image = Files.readString(imagePath, StandardCharsets.UTF_8);
            postDTO.setImage(image);
        }
    }

    private void updatePostImage(PostDTO postDTO, Post post) throws IOException {
        if (!postDTO.getImage().isEmpty()) {
            Path imagePath = Paths.get(POST_IMAGE_DIRECTORY + post.getImage());

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(postDTO.getImage().getBytes(), outputStream);
            } catch (IOException e) {
                throw new IOException(e);
            }

        } else {
            postDTO.setImage("");
            postDTO.setImageName("");
        }
    }

    private List<PostDTO> convertToPostDTOList(List<Post> posts) throws IOException {
        List<PostDTO> postsDTO = new ArrayList<>();

        for (Post post : posts) {
            postsDTO.add(PostDTO.convertToDTO(post));
        }

        for (PostDTO postDTO : postsDTO) {
            loadPostImage(postDTO);
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
