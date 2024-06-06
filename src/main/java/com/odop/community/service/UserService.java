package com.odop.community.service;

import com.odop.community.dto.User;

public interface UserService {
    void join(User user);
    boolean validateDuplicatedEmail(String email);
    boolean validateDuplicatedNickname(String nickname);
    boolean validateAccount(String email, String password);
    User findById(long id);
    void update(User user);
    void withdraw(long id);
}
