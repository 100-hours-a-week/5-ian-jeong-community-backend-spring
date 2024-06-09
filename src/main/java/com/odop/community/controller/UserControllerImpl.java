package com.odop.community.controller;

import com.odop.community.domain.ResponseMessage;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    @GetMapping("/email")
    public ResponseEntity<ResponseMessage<Boolean>> validateEmail(@RequestParam(value="email") String email) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);

        try {
            boolean result = userService.validateDuplicatedEmail(userDTO);
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
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(nickname);

        try {
            boolean result = userService.validateDuplicatedNickname(userDTO);
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
    public ResponseEntity<Void> validateAccount(@RequestBody UserDTO userDTO) {
        try {
            Optional<String> token = userService.validateAccount(userDTO);

            if (token.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.get());

            return ResponseEntity.ok().headers(headers).build();

        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to sign in = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseMessage<UserDTO>> findById(@PathVariable("userId") Long id) {
        UserDTO userDTO = new UserDTO(id, null, null, null, null);

        try {
            userDTO = userService.findById(userDTO);
            ResponseMessage<UserDTO> responseMessage = new ResponseMessage<>(userDTO);

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        } catch(EmptyResultDataAccessException e) {
            log.error("Error attempting to find a user by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to find a user by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log.error("Error attempting to read image = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> modify(@PathVariable("userId") Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);

        try {
            userService.modify(userDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EmptyResultDataAccessException e) {
            log.error("Error attempting to find a user by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to find a user by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(IOException e) {
            log.error("Error attempting to save image = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> modifyPassword(@PathVariable("userId") Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);

        try {
            userService.modifyPassword(userDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to find a user by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> withdraw(@PathVariable("userId") Long id) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userService.withdraw(userDTO);

        } catch (DataAccessResourceFailureException e) {
            log.error("Error attempting to delete a user by id = {}", e.getMessage());
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
