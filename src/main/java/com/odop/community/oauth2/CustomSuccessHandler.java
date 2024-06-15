package com.odop.community.oauth2;

import com.odop.community.controller.auth.AuthController;
import com.odop.community.domain.dto.auth.CustomOAuth2User;
import com.odop.community.domain.entity.RefreshToken;
import com.odop.community.jwt.JWTToken;
import com.odop.community.jwt.JWTUtil;
import com.odop.community.service.auth.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        Long id = customUserDetails.getId();

        JWTToken token = jwtUtil.createJwt(id);

        authService.saveRefreshToken(
                RefreshToken.builder()
                        .userId(id)
                        .token(token.refreshToken())
                        .build()
        );

        response.addCookie(createCookie("Authorization", token.accessToken()));
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(3600000);

        cookie.setPath("/"); // 해당 도메인 하위 경로에서 쿠키 유효
        cookie.setHttpOnly(true);

        return cookie;
    }
}

