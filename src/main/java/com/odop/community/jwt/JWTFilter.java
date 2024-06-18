package com.odop.community.jwt;

import com.odop.community.auth.CustomUserDetails;
import com.odop.community.auth.RefreshTokenService;
import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.entity.RefreshToken;
import com.odop.community.domain.entity.User;
import com.odop.community.oauth2.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private static final String ACCESS_TOKEN_KEY = "Authorization";

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/login",
            "/users/email",
            "/users/password",
            "/users",
            "/oauth2/authorization/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if (EXCLUDE_URLS.contains(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = Long.parseLong(request.getHeader("user-id"));

        Cookie[] cookies = request.getCookies(); // 쿠키가 없다면 인증토큰을 아직 발급받지 못한 상황
        if (cookies == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = Arrays.stream(cookies)
                .filter(cookie -> ACCESS_TOKEN_KEY.equals(cookie.getName()))
                .findFirst()
                .map(cookie -> URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8))
                .orElse(null);

        if(!isToken(authorization)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 여기서 필터체인으로 념기면 내부적으로 인증이안됐음을 알고 8080/login 200 OK 로 리다이렉트때려버림 그래서 주석 ㄱㄱ
//            filterChain.doFilter(request, response);
            return;
        }

        try {
            if(isExpired(authorization)) { // 만료되었다면
                String refreshToken = findRefreshToken(userId);

                if (isExpired(refreshToken)) { // 리프레쉬도 만료되었다면
                    discardRefreshToken(userId);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // 리프레쉬 토큰이 살아있다면
                JWTToken jwtToken = reissueJwt(userId);
                discardRefreshToken(userId);
                saveRefreshToken(userId, jwtToken.refreshToken());
                response.addCookie(createCookie("Authorization", URLEncoder.encode(jwtToken.accessToken(), StandardCharsets.UTF_8)));


                log.info("{} pass authentication", userId);
                filterChain.doFilter(request, response);

                return;
            }


            User user = new User();
            user.setEmail("아무거나 가능");
            user.setPassword("의미 jwt 검증 통과함 히히");

            CustomUserDetails customUserDetails = new CustomUserDetails(user);

            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken); //세션에 사용자 등록

            log.info("{} pass authentication", userId);
            filterChain.doFilter(request, response);
            // 일반유저 로직도 필요함
            // 유효한 토큰이라면 검증된 요소 담고 다음 필터로 ㄱㄱ
            // OAuth
//            SecurityContextHolder.getContext().setAuthentication(createOAuthUserToken(userId));

        } catch (EmptyResultDataAccessException e) {
            log.error("Refresh token not found = {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }




    private JWTToken reissueJwt(Long userId) {
        return jwtUtil.createJwt(userId);
    }

    private void saveRefreshToken(Long userId, String refreshToken) {
        refreshTokenService.save(
                RefreshToken.builder()
                        .userId(userId)
                        .token(refreshToken)
                        .build()
        );
    }

    private void discardRefreshToken(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }

    private String findRefreshToken(Long userId) {
        return refreshTokenService.findByUserId(userId);
    }

    private boolean isExpired(String token) {
        return jwtUtil.isExpired(token);
    }

    private Authentication createOAuthUserToken(Long id) {
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(
                UserDTO.builder()
                        .id(id)
                        .build()
        );

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        return authToken;
    }


    private boolean isToken(String authorization) {
        return authorization != null && authorization.startsWith("Bearer ");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(3600000);

        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
