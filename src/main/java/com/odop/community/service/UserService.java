package com.odop.community.service;

import com.odop.community.domain.dto.UserDTO;
import org.springframework.dao.DataAccessResourceFailureException;

public interface UserService {
    void join(UserDTO userDTO);
    boolean validateDuplicatedEmail(String email) throws DataAccessResourceFailureException;
    boolean validateDuplicatedNickname(String nickname);
    boolean validateAccount(String email, String password);
    UserDTO findById(long id);
    void update(UserDTO userDTO);
    void withdraw(long id);
}
