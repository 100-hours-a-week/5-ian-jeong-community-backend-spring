package com.odop.community.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {
    private String email;
    private String password;
    private String nickname;
    private String image;
}
