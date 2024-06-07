package com.odop.community.domain.dto;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String image;

    private final PasswordEncoder passwordEncoder;


    public void encodePassword() {
        password = passwordEncoder.encode(password);
    }
}
