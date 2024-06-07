package com.odop.community.repository;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(UserDTO userDTO) {
        String sql = "INSERT INTO users (email, password, nickname, image) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getNickname(),
                userDTO.getImage()
        );
    }

    @Override
    public List<User> selectAll() throws DataAccessException {
        try {
            String sql = "SELECT " +
                    "id, " +
                    "email, " +
                    "password, " +
                    "nickname, " +
                    "convert(image USING UTF8) as image, " +
                    "DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at, " +
                    "DATE_FORMAT(updated_at, '%Y-%m-%d %H:%i:%s') AS updated_at, " +
                    "DATE_FORMAT(deleted_at, '%Y-%m-%d %H:%i:%s') AS deleted_at " +
                    "FROM users " +
                    "WHERE deleted_at IS NULL";

            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing selectAll query", e);
        }
    }

    @Override
    public UserDTO selectById(long id) {
        String sql = "SELECT id, email, password, nickname, convert(image USING UTF8) as image FROM users WHERE id = ? AND deleted_at IS NULL";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(UserDTO.class), id);
    }

    @Override
    public void update(UserDTO userDTO) {
        String sql = "UPDATE users SET password = ?, nickname = ?, image = ? WHERE id = ? AND deleted_at IS NULL";
        jdbcTemplate.update(
                sql,
                userDTO.getPassword(),
                userDTO.getNickname(),
                userDTO.getImage(),
                userDTO.getId()
        );
    }

    @Override
    public void delete(long id) {
        String sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}


