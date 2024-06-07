package com.odop.community.domain.dto;

import com.odop.community.domain.entity.User;

import java.util.List;

public class UsersDTO {
    private final List<User> users;

    public UsersDTO(List<User> users) {
        this.users = users;
    }

    public boolean validateDuplicatedEmail(String email) {
        for (User user : users) {
            if(user.getEmail().equals(email)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateDuplicatedNickname(String nickname) {
        for (User user : users) {
            if (user.getNickname().equals(nickname)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateAccount(String email, String password) {
        // 순회하면서 아이디 비번 검증
        for (User users : users) {
            // 해시 검증 해야함
        }

        return false;
    }
}
