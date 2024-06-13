package com.odop.community.controller.auth;

import com.odop.community.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<?> getUserId(String accessToken);
    ResponseEntity<?> validateAccount(UserDTO userDTO);
    ResponseEntity<?> validateRefreshToken(String refreshToken, UserDTO userDTO);
}
