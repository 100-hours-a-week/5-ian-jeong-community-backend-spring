package com.odop.community.util;

import com.odop.community.auth.JWTToken;
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

    public static ResponseEntity<Void> handleResponse(Optional<JWTToken> token) {
        if (token.isEmpty()) {
            throw new IllegalArgumentException();
        }

        JWTToken jwtToken = token.get();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken.accessToken());
        headers.add("RefreshToken", "Bearer " + jwtToken.refreshToken());

        return ResponseEntity.ok().headers(headers).build();
    }
}
