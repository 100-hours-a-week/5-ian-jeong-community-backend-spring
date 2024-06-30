package com.odop.community.service.user;

import com.odop.community.domain.collection.Users;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.entity.User;
import com.odop.community.repository.user.UserRepository;
import com.odop.community.service.aws.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.odop.community.enums.Provider.LOCAL;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${user.image.directory}")
    private String USER_IMAGE_DIRECTORY;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    @Override
    public Boolean validateDuplicatedEmail(String email) {
        Users users = new Users(userRepository.selectAll());
        return users.validateDuplicatedEmail(email);
    }

    @Override
    public Boolean validateDuplicatedNickname(String nickname) {
        Users users = new Users(userRepository.selectAll());
        return users.validateDuplicatedNickname(nickname);
    }

    @Override
    public void join(UserDTO userDTO, MultipartFile multipartFile) throws IOException {
        String imagePath = "";

        if (multipartFile != null && !multipartFile.isEmpty()) {
            imagePath = s3Uploader.upload(multipartFile, USER_IMAGE_DIRECTORY);
        }

        userDTO.setImage(imagePath);
        userDTO.encodePassword(passwordEncoder::encode);
        userDTO.setProvider(LOCAL.getName());

        userRepository.insert(userDTO.convertToEntity());
    }

    @Override
    public UserDTO findById(Long id) throws IOException {
        User user = userRepository.selectById(id);

        return UserDTO.convertToDTO(user);
    }

    @Override
    public void modify(UserDTO userDTO, MultipartFile multipartFile) throws IOException {
        User user = userRepository.selectById(userDTO.getId());

        String imagePath = "";

        // 기존이미지가 있다면 삭제 필요
        if(!user.getImage().isEmpty() && multipartFile != null && !multipartFile.isEmpty()) {
            imagePath = s3Uploader.updateFile(multipartFile, user.getImage(), USER_IMAGE_DIRECTORY);
        }

        // 기존이미지가 없고 업데이트 이미지가 있다면
        if(user.getImage().isEmpty() && multipartFile != null && !multipartFile.isEmpty()) {
            imagePath = s3Uploader.upload(multipartFile, USER_IMAGE_DIRECTORY);
        }

        userDTO.setImage(imagePath);
        userRepository.update(userDTO.convertToEntity());
    }

    @Override
    public void modifyPassword(UserDTO userDTO) {
        userDTO.encodePassword(passwordEncoder::encode);
        userRepository.updatePassword(userDTO.convertToEntity());
    }

    @Override
    public void withdraw(Long id) {
        userRepository.delete(id);
    }
}
