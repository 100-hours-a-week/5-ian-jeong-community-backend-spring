package com.odop.community.repository;

import com.odop.community.dto.User;
import com.odop.community.dto.Users;

import java.util.Optional;

public interface UserRepository {
    void insert(User user);
    Optional<Users> selectUsers();
}
