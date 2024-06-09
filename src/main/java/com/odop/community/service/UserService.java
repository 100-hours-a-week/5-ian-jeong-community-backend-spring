package com.odop.community.service;

import com.odop.community.domain.dto.UserDTO;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    boolean validateDuplicatedEmail(UserDTO userDTO);
    boolean validateDuplicatedNickname(UserDTO userDTO);
    void join(UserDTO userDTO) throws IOException;
    Optional<String> validateAccount(UserDTO userDTO);
    UserDTO findById(UserDTO userDTO) throws IOException;
    void modify(UserDTO userDTO) throws IOException;
    void modifyPassword(UserDTO userDTO);
    void withdraw(UserDTO userDTO);
}
