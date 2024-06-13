package com.odop.community.domain.dto;

import com.odop.community.domain.entity.User;
import com.odop.community.functional.Encoder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  // object mapper 를 위한 기본 생성자
@AllArgsConstructor  // entity 에서 응답을 위한 DTO 로 변환할 때 사용
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String image;

    public void encodePassword(Encoder encoder) {
        password = encoder.encode(password);
    }

    public User convertToEntity() {
        return new User(id, email, password, nickname, image);
    }

    public static UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getImage()
        );
    }
}
