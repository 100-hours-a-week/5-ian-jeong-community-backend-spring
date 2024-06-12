package com.odop.community.controller.auth;

import com.odop.community.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthController {
    ResponseEntity<?> getUserId(String accessToken);
    ResponseEntity<?> validateAccount(UserDTO userDTO);
    ResponseEntity<?> validateRefreshToken(String refreshToken, UserDTO userDTO);
}
