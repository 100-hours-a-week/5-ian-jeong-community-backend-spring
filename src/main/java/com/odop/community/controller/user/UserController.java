package com.odop.community.controller.user;

import com.odop.community.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserController {
    ResponseEntity<?> validateEmail(String email);
    ResponseEntity<?> validateNickname(String password);
    ResponseEntity<?> join(UserDTO userDTO, MultipartFile multipartFile);
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> modify(Long id, UserDTO userDTO, MultipartFile multipartFile);
    ResponseEntity<?> modifyPassword(Long id, UserDTO userDTO);
    ResponseEntity<?> withdraw(Long id);
}
