package com.odop.community.domain.dto;

import com.odop.community.domain.entity.User;
import com.odop.community.functional.Encoder;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor  // object mapper 를 위한 기본 생성자
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String image;
    private String provider;

    public void encodePassword(Encoder encoder) {
        password = encoder.encode(password);
    }

    public User convertToEntity() {
        return new User(id, email, password, nickname, image, provider);
    }

    public static UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .image(user.getImage())
                .provider(user.getProvider())
                .build();
    }
}
