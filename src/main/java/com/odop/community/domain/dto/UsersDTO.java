package com.odop.community.domain.dto;

import com.odop.community.domain.entity.User;
import com.odop.community.service.UserServiceImpl;

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

    public boolean validateAccount(UserDTO userDTO, UserServiceImpl.PasswordValidator passwordValidator) {
        for (User user : users) {
            if (user.getEmail().equals(userDTO.getEmail())) {
                if (passwordValidator.validate(userDTO.getPassword(), user.getPassword())) {
                    return true;
                }
            }
        }

        return false;
    }
}
