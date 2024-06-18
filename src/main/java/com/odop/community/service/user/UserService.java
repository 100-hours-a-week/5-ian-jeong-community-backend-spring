package com.odop.community.service.user;

import com.odop.community.domain.dto.UserDTO;

import java.io.IOException;

public interface UserService {
    Long extractIdFromToken(String accessToken);
    Boolean validateDuplicatedEmail(String email);
    Boolean validateDuplicatedNickname(String password);
    void join(UserDTO userDTO) throws IOException;
    UserDTO findById(Long id) throws IOException;
    void modify(UserDTO userDTO) throws IOException;
    void modifyPassword(UserDTO userDTO);
    void withdraw(Long id);
}
