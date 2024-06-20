package com.odop.community.service.user;

import com.odop.community.domain.collection.Users;
import com.odop.community.domain.dto.UserDTO;
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

import static com.odop.community.enums.Provider.LOCAL;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_IMAGE_DIRECTORY = "src/main/resources/images/user/";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    public void join(UserDTO userDTO) throws IOException {
        storeUserImage(userDTO);
        userDTO.encodePassword(passwordEncoder::encode);
        userDTO.setProvider(LOCAL.getName());
        userRepository.insert(userDTO.convertToEntity());
    }

    @Override
    public UserDTO findById(Long id) throws IOException {
        User user = userRepository.selectById(id);
        loadUserImage(user);

        return UserDTO.convertToDTO(user);
    }

    @Override
    public void modify(UserDTO userDTO) throws IOException {
        User user = userRepository.selectById(userDTO.getId());
        updateUserImage(userDTO, user);

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
        if (user.getImage() != null && !user.getImage().isEmpty()) {
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + user.getImage());
            String image = Files.readString(imagePath, StandardCharsets.UTF_8);
            user.setImage(image);
        }
    }

    private void updateUserImage(UserDTO userDTO, User user) throws IOException {
        if (userDTO.getImage() != null && !userDTO.getImage().isEmpty()) {
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
