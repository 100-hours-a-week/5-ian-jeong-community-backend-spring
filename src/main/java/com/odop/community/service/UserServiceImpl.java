package com.odop.community.service;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.dto.UsersDTO;
import com.odop.community.domain.entity.User;
import com.odop.community.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserServiceImpl implements UserService {
    private static final String USER_IMAGE_DIRECTORY = "src/main/resources/images/user/";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean validateDuplicatedEmail(String email) throws DataAccessResourceFailureException {
        try {
            UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
            return usersDTO.validateDuplicatedEmail(email);

        } catch(DataAccessResourceFailureException e) {
            throw new DataAccessResourceFailureException(null, e);
        }
    }



    @Override
    public boolean validateDuplicatedNickname(String nickname) throws DataAccessResourceFailureException {
        try {
            UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
            return usersDTO.validateDuplicatedNickname(nickname);

        } catch (DataAccessResourceFailureException e) {
            throw new DataAccessResourceFailureException(null, e);
        }
    }

    @Override
    public void join(UserDTO userDTO) {
        if (!userDTO.getImage().equals("")) {
            String imageName = UUID.randomUUID().toString();
            Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + imageName);

            try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                FileCopyUtils.copy(userDTO.getImage().getBytes(), outputStream);
                userDTO.setImage(imageName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.insert(userDTO);
    }

    @Override
    public boolean validateAccount(String email, String password) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
        // 검증 통과여부에 따라 jwt 증 발급해야함
        return usersDTO.validateAccount(email, password);
    }




    @Override
    public UserDTO findById(long id) {
        return userRepository.selectById(id); // 옵셔널 처리?
    }

    @Override
    public void update(UserDTO userDTO) {
        userRepository.update(userDTO);
    }

    @Override
    public void withdraw(long id) {
        userRepository.delete(id);
    }
}
