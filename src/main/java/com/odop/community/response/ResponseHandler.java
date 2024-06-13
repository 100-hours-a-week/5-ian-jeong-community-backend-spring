package com.odop.community.response;

import com.odop.community.auth.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public static ResponseEntity<Void> handleResponse(JWTToken token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token.accessToken());
        headers.set("RefreshToken", "Bearer " + token.refreshToken());

        return ResponseEntity.ok().headers(headers).build();
    }
}
