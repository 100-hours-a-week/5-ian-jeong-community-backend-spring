package com.odop.community.auth;

import com.odop.community.domain.entity.RefreshToken;
import com.odop.community.jwt.JWTToken;
import com.odop.community.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Value("${frontend.server.ip}")
    private String frontEndServerIp;

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public LoginFilter(
            JWTUtil jwtUtil,
            AuthenticationManager authenticationManager,
            RefreshTokenService refreshTokenService
    ) {
        this.setFilterProcessesUrl("/api/v1/login");
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // 검증을 위한 계정 바구니
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // 검증 매니저에게 검증 요청
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication
            ) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long id = customUserDetails.getUserId();
        JWTToken token = jwtUtil.createJwt(id);

        refreshTokenService.save(
                        RefreshToken.builder()
                                .userId(id)
                                .token(token.refreshToken())
                                .build()
                        );

        response.addCookie(createCookie("Authorization", URLEncoder.encode(token.accessToken(), StandardCharsets.UTF_8)));
        response.addHeader("user-id", String.valueOf(id));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(3600000);

        cookie.setPath("/");
        cookie.setHttpOnly(false);

        return cookie;
    }
}
