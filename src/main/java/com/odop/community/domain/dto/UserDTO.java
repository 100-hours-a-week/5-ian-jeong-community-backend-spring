package com.odop.community.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String image;

    public UserDTO() {}

    public UserDTO(
            String email,
            String password,
            String nickname,
            String image
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }
}
