package com.odop.community.controller;

import com.odop.community.domain.ResponseData;
import com.odop.community.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Void> join(UserDTO userDTO);
    ResponseEntity<ResponseData<Boolean>> validateEmail(String email);
    ResponseEntity<ResponseData<Boolean>> validateNickname(String nickname);
    ResponseEntity<Void> validateAccount(UserDTO userDTO);
    ResponseEntity<ResponseData<UserDTO>> findById(Long id);
    ResponseEntity<Void> modify(Long id, UserDTO userDTO);
    ResponseEntity<Void> modifyPassword(Long id, UserDTO userDTO);
    ResponseEntity<Void> withdraw(Long id);
}
