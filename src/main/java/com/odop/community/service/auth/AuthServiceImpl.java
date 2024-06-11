package com.odop.community.service.auth;

import com.odop.community.auth.JWTToken;
import com.odop.community.auth.JWTUtil;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.dto.UsersDTO;
import com.odop.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public Optional<JWTToken> validateAccount(UserDTO userDTO) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
        Long id = usersDTO.validateAccount(userDTO, passwordEncoder::matches);

        if(id == 0) {
            return Optional.empty();
        }

        JWTToken token = jwtUtil.createJwt(id);
        return Optional.of(token);
    }

    @Override
    public Optional<JWTToken> validateRefreshToken(String refreshToken, UserDTO userDTO) {
        if (jwtUtil.isExpired(refreshToken)) {
            return Optional.empty();
        }

        JWTToken token = jwtUtil.createJwt(userDTO.getId());
        return Optional.of(token);
    }
}
