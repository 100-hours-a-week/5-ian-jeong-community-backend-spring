package com.odop.community.controller.auth;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.odop.community.constant.ErrorMessage.*;
import static com.odop.community.util.ResponseHandler.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping("/sign-in")
    public ResponseEntity<?> validateAccount(@RequestBody UserDTO userDTO) {
        try {
            return handleResponse(authService.validateAccount(userDTO));

        } catch(IllegalArgumentException e) {
            return handleException(e, ERROR_AUTH, HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return handleException(e, ERROR_SIGN_IN, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping("/refresh-token")
    public ResponseEntity<?> validateRefreshToken(
            @RequestHeader("Authorization") String refreshToken,
            @RequestBody UserDTO userDTO
    ) {
        try {
            return handleResponse(authService.validateRefreshToken(refreshToken, userDTO));

        } catch(IllegalArgumentException e) {
            return handleException(e, ERROR_AUTH, HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return handleException(e, ERROR_RUNTIME, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
