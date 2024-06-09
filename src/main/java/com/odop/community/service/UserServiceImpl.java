package com.odop.community.service;

import com.odop.community.auth.JWTUtil;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.dto.UsersDTO;
import com.odop.community.domain.entity.User;
import com.odop.community.repository.UserRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_IMAGE_DIRECTORY = "src/main/resources/images/user/";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public boolean validateDuplicatedEmail(UserDTO userDTO) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());

        return usersDTO.validateDuplicatedEmail(userDTO.getEmail());
    }

    @Override
    public boolean validateDuplicatedNickname(UserDTO userDTO) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());

        return usersDTO.validateDuplicatedNickname(userDTO.getNickname());
    }

    @Override
    public void join(UserDTO userDTO) throws IOException {
        if (!userDTO.getImage().equals("")) {
            String imageName = UUID.randomUUID().toString();
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + imageName);

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(userDTO.getImage().getBytes(), outputStream);
                userDTO.setImage(imageName);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.insert(userDTO.convertToUserEntity());
    }

    @Override
    public Optional<String> validateAccount(UserDTO userDTO) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());

        if(usersDTO.validateAccount(userDTO, passwordEncoder::matches)) {
            String token = jwtUtil.createJwt(userDTO.getNickname());
            return Optional.of(token);
        }

        return Optional.empty();
    }

    @Override
    public UserDTO findById(UserDTO userDTO) throws IOException {
        User user = userRepository.selectById(userDTO.convertToUserEntity());
        if (!user.getImage().equals("")) {
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + user.getImage());
            String image = Files.readString(imagePath, StandardCharsets.UTF_8);
            user.setImage(image);
        }

        return UserDTO.convertToUserDTO(user);
    }

    @Override
    public void modify(UserDTO userDTO) throws IOException {
        User user = userDTO.convertToUserEntity();
        user = userRepository.selectById(user);

        if (!userDTO.getImage().equals("")) {
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + user.getImage()); // 기존 path 에 저장
            System.out.println(imagePath);
            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(userDTO.getImage().getBytes(), outputStream);

            } catch (IOException e) {
                throw new IOException(e);
            }
        }

        userDTO.setImage(user.getImage());
        userRepository.update(userDTO.convertToUserEntity());
    }

    @Override
    public void modifyPassword(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.updatePassword(userDTO.convertToUserEntity());
    }

    @Override
    public void withdraw(UserDTO userDTO) {
        userRepository.delete(userDTO.convertToUserEntity());
    }

    @FunctionalInterface
    public interface PasswordValidator {
        boolean validate(String rawPassword, String encodedPassword);
    }
}
