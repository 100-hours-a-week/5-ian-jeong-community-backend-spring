package com.odop.community.controller;

import com.odop.community.domain.ResponseMessage;
import com.odop.community.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Void> join(UserDTO userDTO);
    ResponseEntity<ResponseMessage<Boolean>> validateEmail(String email);
    ResponseEntity<ResponseMessage<Boolean>> validateNickname(String nickname);
    ResponseEntity<Void> validateAccount(UserDTO userDTO);
    ResponseEntity<ResponseMessage<UserDTO>> findById(Long id);
    ResponseEntity<Void> modify(Long id, UserDTO userDTO);
    ResponseEntity<Void> modifyPassword(Long id, UserDTO userDTO);
    ResponseEntity<Void> withdraw(Long id);
}
