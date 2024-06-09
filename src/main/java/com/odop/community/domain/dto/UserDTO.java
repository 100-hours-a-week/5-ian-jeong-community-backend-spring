package com.odop.community.domain.dto;

import com.odop.community.domain.entity.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.bcel.BcelRenderer;

import java.time.LocalDateTime;

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
            Long id,
            String email,
            String password,
            String nickname,
            String image
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }

    public User convertToUserEntity() {
        return new User(id, email, password, nickname, image);
    }

    public static UserDTO convertToUserDTO(User user) {
        return new UserDTO(
            user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getImage()
        );
    }
}
