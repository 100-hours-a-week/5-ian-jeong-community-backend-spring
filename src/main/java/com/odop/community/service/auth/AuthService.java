package com.odop.community.service.auth;

import com.odop.community.auth.JWTToken;
import com.odop.community.domain.dto.UserDTO;

public interface AuthService {
    Long getUserId(String accessToken);
    JWTToken validateAccount(UserDTO userDTO);
    JWTToken validateRefreshToken(String refreshToken, UserDTO userDTO);
}
