package com.odop.community.repository;

import com.odop.community.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
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
            "convert(image USING UTF8) as image, " +
            "DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') AS created_at, " +
            "DATE_FORMAT(updated_at, '%Y-%m-%d %H:%i:%s') AS updated_at, " +
            "DATE_FORMAT(deleted_at, '%Y-%m-%d %H:%i:%s') AS deleted_at " +
            "FROM users " +
            "WHERE deleted_at IS NULL";
    private static final String SELECT_BY_ID = "SELECT id, email, password, nickname, convert(image USING UTF8) as image FROM users WHERE id = ? AND deleted_at IS NULL";
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

        } catch(DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing insert query", e);
        }
    }

    @Override
    public List<User> selectAll() {
        try {
            return jdbcTemplate.query(SELECT_ALL, BeanPropertyRowMapper.newInstance(User.class));
            
        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing selectAll query", e);
        }
    }

    @Override
    public User selectById(User user) throws EmptyResultDataAccessException{
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, BeanPropertyRowMapper.newInstance(User.class), user.getId());

        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No user found with ID: " + user.getId(), 1, e);
        } catch(DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing selectById query", e);
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

        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing update query", e);
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

        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing update query", e);
        }
    }

    @Override
    public void delete(User user) {
        try {
            jdbcTemplate.update(DELETE, user.getId());

        } catch (DataAccessException e) {
            throw new DataAccessResourceFailureException("Error executing delete query", e);
        }
    }
}


