package com.odop.community.controller;

import com.odop.community.domain.ResponseMessage;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @GetMapping("/email")
    public ResponseEntity<ResponseMessage<Boolean>> validateEmail(@RequestParam(value="email") String email) {
        try {
            boolean result = userService.validateDuplicatedEmail(email);
            ResponseMessage<Boolean> responseMessage = new ResponseMessage<>(result);

            return new ResponseEntity<>(responseMessage,HttpStatus.OK);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error validating email = {}", e.getMessage());



            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/nickname")
    public ResponseEntity<ResponseMessage<Boolean>> validateNickname(@RequestParam(value="nickname") String nickname) {
        try {
            boolean result = userService.validateDuplicatedNickname(nickname);
            ResponseMessage<Boolean> responseMessage = new ResponseMessage<>(result);

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch(DataAccessResourceFailureException e) {
            log.error("Error validating password = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    @PostMapping
    public ResponseEntity<Void> join(@RequestBody UserDTO userDTO) {
        try {
            userService.join(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to sign up = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("Error attempting to save image = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseMessage<Boolean>> validateAccount(@RequestBody UserDTO userDTO) {
        boolean result = userService.validateAccount(userDTO);
        ResponseMessage<Boolean> responseMessage = new ResponseMessage<>(result);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseMessage<UserDTO>> findById(@PathVariable long id) {
        UserDTO userDTO = userService.findById(id);
        ResponseMessage<UserDTO> responseMessage = new ResponseMessage<>(userDTO);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> update(@RequestBody UserDTO userDTO) {
        userService.update(userDTO); // 이후에 에러처리 필요

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> withdraw(long id) {
        userService.withdraw(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
