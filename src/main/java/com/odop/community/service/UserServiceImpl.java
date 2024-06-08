package com.odop.community.service;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.dto.UsersDTO;
import com.odop.community.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    public void join(UserDTO userDTO) throws IOException, DataAccessResourceFailureException{
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

        try {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.insert(userDTO);

        } catch (DataAccessResourceFailureException e) {
            throw new DataAccessResourceFailureException(null, e);
        }
    }


    @Override
    public boolean validateAccount(UserDTO userDTO) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());

        if(usersDTO.validateAccount(userDTO, passwordEncoder::matches)) {
            // 검증 통과여부에 따라 jwt 증 발급해야함

        }

        // 에러처처리
        return null;
    }




    @Override
    public UserDTO findById(long id) {
        // 불러와서 이미지 경로에서불어와야됨
        return userRepository.selectById(id); // 옵셔널 처리?
    }

    @Override
    public void update(UserDTO userDTO) {
        // 이미지 업데이트시 확인해야함

        userRepository.update(userDTO);
    }

    @Override
    public void withdraw(long id) {
        userRepository.delete(id);
    }

    @FunctionalInterface
    public interface PasswordValidator {
        boolean validate(String rawPassword, String encodedPassword);
    }
}
