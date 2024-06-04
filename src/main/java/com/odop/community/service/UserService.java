package com.odop.community.service;

import com.odop.community.dto.User;

public interface UserService {
    void save(User user);
    boolean validateDuplicatedEmail(String email);
    boolean validateDuplicatedNickname(String nickname);
}
