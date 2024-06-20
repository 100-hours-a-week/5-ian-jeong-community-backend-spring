package com.odop.community.controller.user;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.odop.community.constant.ErrorMessage.*;
import static com.odop.community.response.ResponseHandler.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @GetMapping("/email")
    public ResponseEntity<?> validateEmail(@RequestParam("email") String email) {
        try {
            return handleResponse(userService.validateDuplicatedEmail(email), HttpStatus.OK);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_EMAIL_VALIDATION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/nickname")
    public ResponseEntity<?> validateNickname(@RequestParam("nickname") String nickname) {
        try {
            return handleResponse(userService.validateDuplicatedNickname(nickname), HttpStatus.OK);

        } catch(RuntimeException e) {
            return handleException(e, ERROR_PASSWORD_VALIDATION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<?> join(@RequestBody UserDTO userDTO) {
        try {
            userService.join(userDTO);
            return handleResponse(HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_JOIN, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return handleException(e, ERROR_STORE_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<?> findById(@PathVariable("userId") Long id) {
        try {
            return handleResponse(userService.findById(id), HttpStatus.OK);

        } catch(EmptyResultDataAccessException e) {
            return handleException(e, ERROR_FIND_USER, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return handleException(e, ERROR_FIND_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return handleException(e, ERROR_LOAD_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    @PatchMapping("/{userId}")
    public ResponseEntity<?> modify(@PathVariable("userId") Long id, @RequestBody UserDTO userDTO) {
        try {
            userDTO.setId(id);
            userService.modify(userDTO);
            return handleResponse(HttpStatus.NO_CONTENT);

        } catch(EmptyResultDataAccessException e) {
            return handleException(e, ERROR_FIND_USER, HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return handleException(e, ERROR_FIND_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IOException e) {
            return handleException(e, ERROR_STORE_IMAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PatchMapping("/{userId}/password")
    public ResponseEntity<?> modifyPassword(@PathVariable("userId") Long id, @RequestBody UserDTO userDTO) {
        try {
            userDTO.setId(id);
            userService.modifyPassword(userDTO);
            return handleResponse(HttpStatus.NO_CONTENT);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_MODIFY_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> withdraw(@PathVariable("userId") Long id) {
        try {
            userService.withdraw(id);
            return handleResponse(HttpStatus.NO_CONTENT);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_DELETE_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
