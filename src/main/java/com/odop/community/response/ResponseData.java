package com.odop.community.response;

import com.odop.community.domain.dto.CommentDTO;
import com.odop.community.domain.dto.PostDTO;
import com.odop.community.domain.dto.PostDetailDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseData<T> {
    private T result;
    private PostDTO post;
    private List<CommentDTO> comments;

    public ResponseData(T result) {
        if (result instanceof PostDetailDTO) {
            post = ((PostDetailDTO) result).getPostDTO();
            comments = ((PostDetailDTO) result).getCommentsDTO();

            return;
        }

        this.result = result;
    }
}
