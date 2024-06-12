package com.odop.community.service.user;

import com.odop.community.domain.dto.UserDTO;

import java.io.IOException;

public interface UserService {
    Boolean validateDuplicatedEmail(UserDTO userDTO);
    Boolean validateDuplicatedNickname(UserDTO userDTO);
    void join(UserDTO userDTO) throws IOException;
    UserDTO findById(UserDTO userDTO) throws IOException;
    void modify(UserDTO userDTO) throws IOException;
    void modifyPassword(UserDTO userDTO);
    void withdraw(UserDTO userDTO);
}
