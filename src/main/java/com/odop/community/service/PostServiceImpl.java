package com.odop.community.service;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostsDTO;
import com.odop.community.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public PostsDTO getAll() {
        return null;
    }

    @Override

    public PostDTO getById() {
        return null;
    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void addComment(CommentDTO commentDTO) {

    }

    @Override
    public void updateComment(CommentDTO commentDTO) {

    }

    @Override
    public void deleteComment(Long id) {

    }
}
