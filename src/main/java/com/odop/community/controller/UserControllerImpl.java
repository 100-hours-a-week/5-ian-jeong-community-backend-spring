package com.odop.community.controller;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.odop.community.constant.ErrorMessage.*;
import static com.odop.community.util.ResponseHandler.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @GetMapping("/email")
    public ResponseEntity<?> validateEmail(@ModelAttribute UserDTO userDTO) {
        try {
            return handleResponse(userService.validateDuplicatedEmail(userDTO), HttpStatus.OK);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_EMAIL_VALIDATION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/nickname")
    public ResponseEntity<?> validateNickname(@ModelAttribute UserDTO userDTO) {
        try {
            return handleResponse(userService.validateDuplicatedNickname(userDTO), HttpStatus.OK);

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
    @PostMapping("/sign-in")
    public ResponseEntity<?> validateAccount(@RequestBody UserDTO userDTO) {
        try {
            return handleResponse(userService.validateAccount(userDTO), HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_SIGN_IN, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<?> findById(@PathVariable("userId") Long id) {
        try {
            UserDTO userDTO = new UserDTO(id);
            return handleResponse(userService.findById(userDTO), HttpStatus.OK);

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
            UserDTO userDTO = new UserDTO(id);
            userService.withdraw(userDTO);
            return handleResponse(HttpStatus.NO_CONTENT);

        } catch (RuntimeException e) {
            return handleException(e, ERROR_DELETE_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
