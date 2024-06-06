package com.odop.community.controller;

import com.odop.community.dto.ResponseMessage;
import com.odop.community.dto.User;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<Void> join(User user);
    ResponseEntity<ResponseMessage<Boolean>> validateEmail(String email);
    ResponseEntity<ResponseMessage<Boolean>> validateNickname(String nickname);
    ResponseEntity<ResponseMessage<Boolean>> validateAccount(String email, String nickname);
    ResponseEntity<ResponseMessage<User>> findById(long id);
    ResponseEntity<Void> update(User user);
    ResponseEntity<Void> withdraw(long id);
}
