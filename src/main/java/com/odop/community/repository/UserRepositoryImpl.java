package com.odop.community.repository;

import com.odop.community.dto.User;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void create(User user) {
        // 쿼리 날려서 저장 여기서 디비 예외처리던짐
        // 예외는 던져서 컨트롤러에서 처리 ㄱㄱ
    }
}
