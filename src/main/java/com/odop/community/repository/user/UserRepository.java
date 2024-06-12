package com.odop.community.repository.user;

import com.odop.community.domain.entity.User;

import java.util.List;

public interface UserRepository {
    void insert(User user);
    List<User> selectAll() ;
    User selectById(User user);
    void update(User user);
    void updatePassword(User user);
    void delete(User user);
}