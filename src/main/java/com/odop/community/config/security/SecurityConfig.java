package com.odop.community.config.security;

import com.odop.community.jwt.JWTFilter;
import com.odop.community.jwt.JWTUtil;
import com.odop.community.auth.LoginFilter;
import com.odop.community.oauth2.CustomOAuth2UserService;
import com.odop.community.oauth2.CustomSuccessHandler;
import com.odop.community.auth.RefreshTokenService;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final JWTFilter jwtFilter;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호 기능을 비활성화
                .formLogin(AbstractHttpConfigurer::disable)  // 폼 기반 로그인을 비활성화(기본 로그인 페이지를 사용하지 않도록 설정) -> UsernamePasswordFilter 비활성화를 의미
                .httpBasic(AbstractHttpConfigurer::disable)  // HTTP Basic 인증을 비활성화
                .cors(Customizer.withDefaults())

                // 요청 경로별 인가, 기존 인증로직만 해당하고 , jwt 필터는 별도로 처리
                .authorizeHttpRequests((auth) -> auth
                            .requestMatchers(
                                    "/users/nickname",     // 닉네임 중복검사
                                    "/users/email",                 // 이메일 중복검사
                                    "/users",                       // post 회원가입
                                    "/login"                        // 로그인 요청
                            ).permitAll()                           // 인증없이 요청가능
                            .anyRequest().authenticated()           // 나머지 경로 인가 필요
                )

                // jwt, 로그인 필터 추가
                .addFilterBefore(jwtFilter, LoginFilter.class)
                .addFilterAt(new LoginFilter(jwtUtil, authenticationConfiguration.getAuthenticationManager(), refreshTokenService), UsernamePasswordAuthenticationFilter.class)

                // OAuth
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint(
                                (userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                )

                // jwt 사용할 때는 세션을 stateless 상태로 관리
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

