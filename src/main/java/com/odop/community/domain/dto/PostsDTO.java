package com.odop.community.domain.dto;

import java.util.List;

public class PostsDTO {
    private final List<PostDTO> postsDTO;

    public PostsDTO(List<PostDTO> postsDTO) {
        this.postsDTO = postsDTO;
        // 엔티티로 관리하는 데이터의 칼럼을 데이터베이스와 불일치하는 사이즈로
        // 할당했을 때, 디비에 쿼리를 날리지 않아도 스프링JPA 데이터무결성 에러를 터뜨림

    }

    public List<PostDTO> getList() {
        return postsDTO;
    }
}
