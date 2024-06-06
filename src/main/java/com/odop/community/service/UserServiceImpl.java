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
    public void join(User user) {
        // 이미지 따로처리하고 경로룰저장해야함
        // id Long 타입으로 변경? 디비도 변경
        // 도메인 엔티티객체 어노테이션 추가
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

    @Override
    public boolean validateAccount(String email, String password) {
        Users users = new Users(userRepository.selectAll());
        // 검증 통과여부에 따라 jwt 증 발급해야함
        return users.validateAccount(email, password);
    }

    @Override
    public User findById(long id) {
        return userRepository.selectById(id); // 옵셔널 처리?
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void withdraw(long id) {
        userRepository.delete(id);
    }
}
