package com.odop.community.repository;

import com.odop.community.domain.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(User user) {
        try {
            String sql = "INSERT INTO users (email, password, nickname, image) " +
                    "VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(
                    sql,
                    user.getEmail(),
                    user.getPassword(),
                    user.getNickname(),
                    user.getImage()
            );

        } catch(DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing insert query", e);
        }
    }

    @Override
    public List<User> selectAll() {
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
    public User selectById(User user) throws EmptyResultDataAccessException{
        try {
            String sql = "SELECT id, email, password, nickname, convert(image USING UTF8) as image FROM users WHERE id = ? AND deleted_at IS NULL";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), user.getId());

        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No user found with ID: " + user.getId(), 1, e);
        } catch(DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing selectById query", e);
        }
    }

    @Override
    public void update(User user) {
        try {
            String sql = "UPDATE users SET nickname = ?, image = ? WHERE id = ? AND deleted_at IS NULL";
            jdbcTemplate.update(
                    sql,
                    user.getNickname(),
                    user.getImage(),
                    user.getId()
            );
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing update query", e);
        }
    }

    @Override
    public void updatePassword(User user) {
        try {
            String sql = "UPDATE users SET password = ? WHERE id = ? AND deleted_at IS NULL";
            jdbcTemplate.update(
                    sql,
                    user.getPassword(),
                    user.getId()
            );
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing update query", e);
        }
    }

    @Override
    public void delete(User user) {
        try {
            String sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP() WHERE id = ?";
            jdbcTemplate.update(sql, user.getId());

        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing delete query", e);
        }
    }
}


