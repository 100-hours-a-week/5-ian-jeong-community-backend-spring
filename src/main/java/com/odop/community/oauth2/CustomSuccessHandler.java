package com.odop.community.oauth2;

import com.odop.community.domain.entity.RefreshToken;
import com.odop.community.jwt.JWTToken;
import com.odop.community.jwt.JWTUtil;
import com.odop.community.auth.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Value("${frontend.server.ip}")
    private String frontEndServerIp;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        Long id = customUserDetails.getId();

        JWTToken token = jwtUtil.createJwt(id);

        refreshTokenService
                .save(
                RefreshToken.builder()
                        .userId(id)
                        .token(token.refreshToken())
                        .build()
        );

        response.addCookie(createCookie("Authorization", URLEncoder.encode(token.accessToken(), StandardCharsets.UTF_8)));
        response.addCookie(createCookie("user-id", String.valueOf(id)));
        response.sendRedirect(frontEndServerIp + "/posts");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(3600000);

        cookie.setPath("/");
//        cookie.setHttpOnly(true);

        return cookie;
    }
}

