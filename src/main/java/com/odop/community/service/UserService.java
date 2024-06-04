package com.odop.community.service;

import com.odop.community.dto.User;

public interface UserService {
    void join(User user);
    boolean validateDuplicatedEmail(String email);
    boolean validateDuplicatedNickname(String nickname);
    boolean validateAccount(String email, String password);
    User findById(int id);
    void update(User user);
    void withdraw(int id);
}
