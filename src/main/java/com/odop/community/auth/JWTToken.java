package com.odop.community.auth;

public record JWTToken(String accessToken, String refreshToken) {}
