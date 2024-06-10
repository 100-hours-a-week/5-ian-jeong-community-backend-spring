package com.odop.community.service;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;
import com.odop.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final String POST_IMAGE_DIRECTORY = "src/main/resources/images/post/";

    private final PostRepository postRepository;

    @Override
    public void add(PostDTO postDTO) throws IOException {
        if (!postDTO.getImage().equals("")) {
            String imageName = UUID.randomUUID().toString();
            Path imagePath = Paths.get(POST_IMAGE_DIRECTORY + imageName);

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(postDTO.getImage().getBytes(), outputStream);
                postDTO.setImage(imageName);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }

        postRepository.insert(postDTO.convertToPostEntity());
    }

    @Override
    public PostsDTO findAll() throws IOException {
        List<Post> postList = postRepository.selectAll();

        for (Post post : postList) {
            if (!post.getImage().equals("")) {
                Path imagePath = Paths.get(POST_IMAGE_DIRECTORY + post.getImage());
                String image = Files.readString(imagePath, StandardCharsets.UTF_8);
                post.setImage(image);
            }
        }

        PostsDTO postsDTO = new PostsDTO(postRepository.selectAll());

        return postsDTO;
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PostDetailDTO findById(PostDTO postDTO) {
        Post post = postRepository.selectById(postDTO.convertToPostEntity());

        if (post == null) {
            throw new RuntimeException("Post not found");
        }


        List<Comment> comments = postRepository.selectAllComments(postDTO);
        return new PostDetailDTO(post, comments);
    }

    @Override
    public void modify(PostDTO postDTO) throws IOException {
        Post post = postDTO.convertToPostEntity();
        post = postRepository.selectById(post);

        if (!postDTO.getImage().equals("")) {
            Path imagePath = Paths.get(POST_IMAGE_DIRECTORY + post.getImage());

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(postDTO.getImage().getBytes(), outputStream);
            } catch (IOException e) {
                throw new IOException(e);
            }

        } else {
            postDTO.setImage(null);
            postDTO.setImageName(null);
        }

        postDTO.setImage(post.getImage());
        postRepository.update(postDTO.convertToPostEntity());
    }

    @Override
    public void remove(PostDTO postDTO) {
        postRepository.delete(postDTO.convertToPostEntity());
    }

    @Override
    public void addComment(CommentDTO commentDTO) {
        postRepository.insertComment(commentDTO.convertToEntity());
    }

    @Override
    public void modifyComment(CommentDTO commentDTO) {
        postRepository.updateComment(commentDTO.convertToEntity());
    }

    @Override
    public void removeComment(CommentDTO commentDTO) {
        postRepository.deleteComment(commentDTO.convertToEntity());
    }
}
