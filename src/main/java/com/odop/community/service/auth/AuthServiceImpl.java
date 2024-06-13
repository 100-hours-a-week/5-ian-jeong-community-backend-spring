package com.odop.community.service.auth;

import com.odop.community.auth.JWTToken;
import com.odop.community.auth.JWTUtil;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.collection.Users;
import com.odop.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public Long getUserId(String accessToken) {
        System.out.println(accessToken);
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

        JWTToken token = jwtUtil.createJwt(userDTO.getId());
        return token;
    }
}
