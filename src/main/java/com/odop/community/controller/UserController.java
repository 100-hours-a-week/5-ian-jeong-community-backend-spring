package com.odop.community.controller;

import com.odop.community.domain.ResponseMessage;
import com.odop.community.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Void> join(UserDTO userDTO);
    ResponseEntity<ResponseMessage<Boolean>> validateEmail(String email);
    ResponseEntity<ResponseMessage<Boolean>> validateNickname(String nickname);
    ResponseEntity<ResponseMessage<Boolean>> validateAccount(UserDTO userDTO);
    ResponseEntity<ResponseMessage<UserDTO>> findById(long id);
    ResponseEntity<Void> update(UserDTO userDTO);
    ResponseEntity<Void> withdraw(long id);
}
