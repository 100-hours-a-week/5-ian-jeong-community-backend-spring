package com.odop.community.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private long id;
    private String email;
    private String password;
    private String nickname;
    private String image;

    private final PasswordEncoder passwordEncoder;


    public void encodePassword() {
        password = passwordEncoder.encode(password);
    }
}
