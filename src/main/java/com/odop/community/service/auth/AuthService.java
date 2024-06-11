package com.odop.community.service.auth;

import com.odop.community.auth.JWTToken;
import com.odop.community.domain.dto.UserDTO;

import java.util.Optional;

public interface AuthService {
    Optional<JWTToken> validateAccount(UserDTO userDTO);
    Optional<JWTToken> validateRefreshToken(String refreshToken, UserDTO userDTO);
}
