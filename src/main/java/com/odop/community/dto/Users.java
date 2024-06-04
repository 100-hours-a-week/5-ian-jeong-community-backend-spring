package com.odop.community.dto;

import java.util.List;

public class Users {
    private final List<User> users;

    public Users(List<User> users) {
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
}
