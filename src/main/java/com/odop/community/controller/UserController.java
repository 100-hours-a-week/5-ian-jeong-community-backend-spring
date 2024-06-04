package com.odop.community.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class UserController implements UserOperations {

    @PostMapping("/")
    public void save() {
        // 로그인할때 바디로 데이터 들어옴, 반환 값은 상태코드
        // 이메일, 패스워드, 닉네임, 이미지 들어옴 데이터베이스에는 아이디도 있음


        // 유저 서비스로 생성메서드 호출
        // 유저서비스에서 DAO를 통해서 디비 접근해서 저장
        // 비번 암호화
    }
}
