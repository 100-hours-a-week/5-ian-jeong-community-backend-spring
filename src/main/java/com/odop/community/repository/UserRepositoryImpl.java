package com.odop.community.repository;

import com.odop.community.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO users (email, password, nickname, image) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getImage()
        );
    }

    @Override
    public List<User> selectAll() {
        String sql = "SELECT id, email, password, nickname, convert(image USING UTF8) as image FROM users WHERE deleted_at IS NULL";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public User selectById(int id) {
        String sql = "SELECT id, email, password, nickname, convert(image USING UTF8) as image FROM users WHERE id = ? AND deleted_at IS NULL";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), id);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET password = ?, nickname = ?, image = ? WHERE id = ? AND deleted_at IS NULL";
        jdbcTemplate.update(
                sql,
                user.getPassword(),
                user.getNickname(),
                user.getImage(),
                user.getId()
        );
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}


