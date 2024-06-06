package com.odop.community.controller;

import com.odop.community.dto.ResponseMessage;
import com.odop.community.dto.User;
import com.odop.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @PostMapping("/")
    public ResponseEntity<Void> join(@RequestBody User user) {
        try {
            userService.join(user);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("user save Error = {} ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/email")
    public ResponseEntity<ResponseMessage<Boolean>> validateEmail(@RequestParam String email) {
        boolean result = userService.validateDuplicatedEmail(email);
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>(result);

        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @Override
    @GetMapping("/nickname")
    public ResponseEntity<ResponseMessage<Boolean>> validateNickname(@RequestParam String nickname) {
        boolean result = userService.validateDuplicatedNickname(nickname);
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>(result);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseMessage<Boolean>> validateAccount(@RequestParam String email, String nickname) {
        boolean result = userService.validateAccount(email, nickname);
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>(result);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseMessage<User>> findById(@PathVariable long id) {
        User user = userService.findById(id);
        ResponseMessage<User> responseMessage = new ResponseMessage<>(user);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> update(@RequestBody User user) {
        userService.update(user); // 이후에 에러처리 필요

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> withdraw(long id) {
        userService.withdraw(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
