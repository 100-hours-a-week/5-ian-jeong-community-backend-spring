package com.odop.community.service;

import com.odop.community.dto.User;
import com.odop.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        // user객체 암호화

        userRepository.create(user);
    }
}
