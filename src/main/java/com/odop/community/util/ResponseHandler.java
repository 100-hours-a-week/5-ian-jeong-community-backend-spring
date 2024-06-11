package com.odop.community.util;

import com.odop.community.domain.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@Slf4j
public class ResponseHandler {

    public static ResponseEntity<Void> handleException(Exception e, String errorMessage, HttpStatus status) {
        log.error(errorMessage, e);
        return ResponseEntity.status(status).build();
    }

    public static <T> ResponseEntity<ResponseData<T>> handleResponse(T data, HttpStatus status) {
        ResponseData<T> responseData = new ResponseData<>(data);
        return new ResponseEntity<>(responseData, status);
    }

    public static ResponseEntity<Void> handleResponse(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    public static ResponseEntity<Void> handleResponse(Optional<String> token) {
        if (token.isEmpty()) {
            return handleResponse(HttpStatus.UNAUTHORIZED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token.get());

        return ResponseEntity.ok().headers(headers).build();
    }
}
