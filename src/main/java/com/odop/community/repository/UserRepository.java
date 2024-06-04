package com.odop.community.repository;

import com.odop.community.dto.User;
import com.odop.community.dto.Users;

import java.util.List;

public interface UserRepository {
    void insert(User user);
    List<User> selectAll();
}
