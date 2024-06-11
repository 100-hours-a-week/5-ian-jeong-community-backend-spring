package com.odop.community.controller.user;

import com.odop.community.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<?> validateEmail(UserDTO userDTO);
    ResponseEntity<?> validateNickname(UserDTO userDTO);
    ResponseEntity<?> join(UserDTO userDTO);
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> modify(Long id, UserDTO userDTO);
    ResponseEntity<?> modifyPassword(Long id, UserDTO userDTO);
    ResponseEntity<?> withdraw(Long id);
}
