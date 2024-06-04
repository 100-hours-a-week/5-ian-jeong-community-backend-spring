package com.odop.community.service;

import com.odop.community.dto.User;
import com.odop.community.dto.Users;
import com.odop.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        // 이미지 따로처리하고 경로룰저장해야함
        // id Long 타입으로 변경?
        user.encodePassword();
        userRepository.insert(user);
    }

    @Override
    public boolean validateDuplicatedEmail(String email) {
        Users users = new Users(userRepository.selectAll());

        return users.validateDuplicatedEmail(email);
    }

    @Override
    public boolean validateDuplicatedNickname(String nickname) {
        Users users = new Users(userRepository.selectAll());

        return users.validateDuplicatedNickname(nickname);
    }
}
