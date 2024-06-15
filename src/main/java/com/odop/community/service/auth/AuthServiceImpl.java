package com.odop.community.service.auth;

import com.odop.community.domain.entity.RefreshToken;
import com.odop.community.jwt.JWTToken;
import com.odop.community.jwt.JWTUtil;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.collection.Users;
import com.odop.community.repository.auth.RefreshTokenRepository;
import com.odop.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public Long getUserId(String accessToken) {
        if(jwtUtil.isExpired(accessToken)) {
            throw new IllegalArgumentException();
        }

        return jwtUtil.getId(accessToken);
    }

    @Override
    public JWTToken validateAccount(UserDTO userDTO) {
        Users users = new Users(userRepository.selectAll());
        Long id = users.validateAccount(userDTO, passwordEncoder::matches);

        if(id == 0) {
            throw  new IllegalArgumentException();
        }

        return jwtUtil.createJwt(id);
    }

    @Override
    public JWTToken validateRefreshToken(String refreshToken, UserDTO userDTO) {
        if (jwtUtil.isExpired(refreshToken)) {
            throw  new IllegalArgumentException();
        }

        return jwtUtil.createJwt(userDTO.getId());
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }


}
