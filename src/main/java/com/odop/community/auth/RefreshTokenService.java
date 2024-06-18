package com.odop.community.auth;

import com.odop.community.domain.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }

    public String findByUserId(Long userId) {
        try {
            return refreshTokenRepository.findByUserId(userId).getToken();

        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("There's no refresh token for user with " + String.valueOf(userId), 1, e);
        }
    }
}
