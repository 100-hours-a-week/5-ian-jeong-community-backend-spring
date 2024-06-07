package com.odop.community.service;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.dto.UsersDTO;
import com.odop.community.domain.entity.User;
import com.odop.community.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean validateDuplicatedEmail(String email) throws DataAccessResourceFailureException {
        try {
            UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
            return usersDTO.validateDuplicatedEmail(email);

        } catch(DataAccessResourceFailureException e) {
            throw new DataAccessResourceFailureException(null, e);
        }
    }



    @Override
    public boolean validateDuplicatedNickname(String nickname) throws DataAccessResourceFailureException {
        try {
            UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
            return usersDTO.validateDuplicatedNickname(nickname);

        } catch (DataAccessResourceFailureException e) {
            throw new DataAccessResourceFailureException(null, e);
        }
    }

    @Override
    public void join(UserDTO userDTO) {
        // 이미지 따로처리하고 경로 저장해야함
        // id Long 타입으로 변경? 디비도 변경


//        userDTO.encodePassword();
//        User user = new User();
//        userRepository.insert(userDTO);
    }




    @Override
    public boolean validateAccount(String email, String password) {
        UsersDTO usersDTO = new UsersDTO(userRepository.selectAll());
        // 검증 통과여부에 따라 jwt 증 발급해야함
        return usersDTO.validateAccount(email, password);
    }

    @Override
    public UserDTO findById(long id) {
        return userRepository.selectById(id); // 옵셔널 처리?
    }

    @Override
    public void update(UserDTO userDTO) {
        userRepository.update(userDTO);
    }

    @Override
    public void withdraw(long id) {
        userRepository.delete(id);
    }
}
