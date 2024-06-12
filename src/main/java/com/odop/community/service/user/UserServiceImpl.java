package com.odop.community.service.user;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.dto.UsersDTO;
import com.odop.community.domain.entity.User;
import com.odop.community.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_IMAGE_DIRECTORY = "src/main/resources/images/user/";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean validateDuplicatedEmail(UserDTO userDTO) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
        return usersDTO.validateDuplicatedEmail(userDTO.getEmail());
    }

    @Override
    public Boolean validateDuplicatedNickname(UserDTO userDTO) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
        return usersDTO.validateDuplicatedNickname(userDTO.getNickname());
    }

    @Override
    public void join(UserDTO userDTO) throws IOException {
        storeUserImage(userDTO);
        userDTO.encodePassword(passwordEncoder::encode);
        userRepository.insert(userDTO.convertToUserEntity());
    }

    @Override
    public UserDTO findById(UserDTO userDTO) throws IOException {
        User user = userRepository.selectById(userDTO.convertToUserEntity());
        loadUserImage(user);

        return UserDTO.convertToUserDTO(user);
    }

    @Override
    public void modify(UserDTO userDTO) throws IOException {
        User user = userRepository.selectById(userDTO.convertToUserEntity());
        updateUserImage(userDTO, user);

        userRepository.update(userDTO.convertToUserEntity());
    }

    @Override
    public void modifyPassword(UserDTO userDTO) {
        userDTO.encodePassword(passwordEncoder::encode);
        userRepository.updatePassword(userDTO.convertToUserEntity());
    }

    @Override
    public void withdraw(UserDTO userDTO) {
        userRepository.delete(userDTO.convertToUserEntity());
    }

    private void storeUserImage(UserDTO userDTO) throws IOException {
        if (!userDTO.getImage().isEmpty()) {
            String imageName = UUID.randomUUID().toString();
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + imageName);

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(userDTO.getImage().getBytes(), outputStream);
                userDTO.setImage(imageName);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }

    private void loadUserImage(User user) throws IOException {
        if (!user.getImage().isEmpty()) {
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + user.getImage());
            String image = Files.readString(imagePath, StandardCharsets.UTF_8);
            user.setImage(image);
        }
    }

    private void updateUserImage(UserDTO userDTO, User user) throws IOException {
        if (!userDTO.getImage().isEmpty()) {
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + user.getImage()); // 기존 path 에 저장

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(userDTO.getImage().getBytes(), outputStream);
                userDTO.setImage(user.getImage());
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
    }
}
