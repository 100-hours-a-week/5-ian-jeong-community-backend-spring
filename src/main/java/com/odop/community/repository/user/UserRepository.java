package com.odop.community.repository.user;

import com.odop.community.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void insert(User user);
    List<User> selectAll() ;
    User selectById(Long id);
    Optional<User> findByNickname(String nickname);
    void update(User user);
    void updatePassword(User user);
    void delete(Long id);
}
