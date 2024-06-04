package com.odop.community.repository;

import com.odop.community.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (email, password, nickname, image) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql, user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getImage()
        );
    }
}
