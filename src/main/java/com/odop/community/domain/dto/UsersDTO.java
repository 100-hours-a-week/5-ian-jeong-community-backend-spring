package com.odop.community.domain.dto;

import com.odop.community.domain.entity.User;
import com.odop.community.functional.PasswordValidator;

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

    public Long validateAccount(UserDTO userDTO, PasswordValidator passwordValidator) {
        for (User user : users) {
            if (user.getEmail().equals(userDTO.getEmail())) {
                if (passwordValidator.validate(userDTO.getPassword(), user.getPassword())) {
                    return user.getId();
                }
            }
        }

        return 0L;
    }
}
