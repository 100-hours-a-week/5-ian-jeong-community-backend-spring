package com.odop.community.service.user;

import com.odop.community.domain.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    Boolean validateDuplicatedEmail(String email);
    Boolean validateDuplicatedNickname(String password);
    void join(UserDTO userDTO, MultipartFile multipartFile) throws IOException;
    UserDTO findById(Long id) throws IOException;
    void modify(UserDTO userDTO, MultipartFile multipartFile) throws IOException;
    void modifyPassword(UserDTO userDTO);
    void withdraw(Long id);
}
