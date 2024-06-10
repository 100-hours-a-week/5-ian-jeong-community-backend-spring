package com.odop.community.domain;

import com.odop.community.domain.dto.PostDetailDTO;
import com.odop.community.domain.entity.Comment;
import com.odop.community.domain.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseData<T> {
    private T result;
    private Post post;
    private List<Comment> comments;

    public ResponseData(T result) {
        if (result instanceof PostDetailDTO) {
            post = ((PostDetailDTO) result).getPost();
            comments = ((PostDetailDTO) result).getComments();

            return;
        }
        this.result = result;
    }
}
