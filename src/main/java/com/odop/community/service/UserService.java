package com.odop.community.service;

import com.odop.community.domain.dto.UserDTO;
import org.springframework.dao.DataAccessResourceFailureException;

import java.io.IOException;

public interface UserService {
    boolean validateDuplicatedEmail(String email) throws DataAccessResourceFailureException;
    boolean validateDuplicatedNickname(String nickname) throws DataAccessResourceFailureException;
    void join(UserDTO userDTO) throws IOException, DataAccessResourceFailureException;
    boolean validateAccount(UserDTO userDTO) throws DataAccessResourceFailureException;
    UserDTO findById(long id);
    void update(UserDTO userDTO);
    void withdraw(long id);
}
