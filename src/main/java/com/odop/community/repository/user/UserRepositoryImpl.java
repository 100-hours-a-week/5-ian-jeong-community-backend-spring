package com.odop.community.repository.user;

import com.odop.community.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private static final String INSERT = "INSERT INTO users (email, password, nickname, image) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT " +
            "id, " +
            "email, " +
            "password, " +
            "nickname, " +
            "image, " +
            "DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at, " +
            "DATE_FORMAT(updated_at, '%Y-%m-%d %H:%i:%s') AS updated_at, " +
            "DATE_FORMAT(deleted_at, '%Y-%m-%d %H:%i:%s') AS deleted_at " +
            "FROM users " +
            "WHERE deleted_at IS NULL";
    private static final String SELECT_BY_ID = "SELECT id, email, password, nickname, image FROM users WHERE id = ? AND deleted_at IS NULL";
    private static final String UPDATE = "UPDATE users SET nickname = ?, image = ? WHERE id = ? AND deleted_at IS NULL";
    private static final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE id = ? AND deleted_at IS NULL";
    private static final String DELETE = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(User user) {
        try {
            jdbcTemplate.update(
                    INSERT,
                    user.getEmail(),
                    user.getPassword(),
                    user.getNickname(),
                    user.getImage()
            );

        } catch(RuntimeException e) {
            throw new RuntimeException("Query to insert new user failed => [" + user.getEmail() + "]", e);
        }
    }

    @Override
    public List<User> selectAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(User.class));

        } catch (RuntimeException e) {
            throw new RuntimeException("Query to select all users failed", e);
        }
    }

    @Override
    public User selectById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(User.class), id);

        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("User with id not found => [" + id + "]", 1, e);
        } catch(RuntimeException e) {
            throw new RuntimeException("Query to select a user failed", e);
        }
    }

    @Override
    public void update(User user) {
        try {
            jdbcTemplate.update(
                    UPDATE,
                    user.getNickname(),
                    user.getImage(),
                    user.getId()
            );

        } catch (RuntimeException e) {
            throw new RuntimeException("Query to update a user failed", e);
        }
    }

    @Override
    public void updatePassword(User user) {
        try {
            jdbcTemplate.update(
                    UPDATE_PASSWORD,
                    user.getPassword(),
                    user.getId()
            );

        } catch (RuntimeException e) {
            throw new RuntimeException("Query to update a user's password failed", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            jdbcTemplate.update(DELETE, id);

        } catch (RuntimeException e) {
            throw new RuntimeException("Query to delete a user failed", e);
        }
    }
}


