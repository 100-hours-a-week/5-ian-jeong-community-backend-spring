package com.odop.community.repository;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.entity.User;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void insert(UserDTO userDTO);
    List<User> selectAll() throws DataAccessException;
    UserDTO selectById(long id);
    void update(UserDTO userDTO);
    public void delete(long id);
}
