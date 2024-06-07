package com.odop.community.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseMessage<T> {
    private final T result;
}
