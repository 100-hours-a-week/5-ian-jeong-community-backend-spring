package com.odop.community.config.security;

import com.odop.community.jwt.JWTFilter;
import com.odop.community.oauth2.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    private final OAuth2UserService oAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호 기능을 비활성화
                .formLogin(AbstractHttpConfigurer::disable)  // 폼 기반 로그인을 비활성화(기본 로그인 페이지를 사용하지 않도록 설정)
                .httpBasic(AbstractHttpConfigurer::disable)  // HTTP Basic 인증을 비활성화
                .cors(withDefaults()) // CorsConfig 작성내용 적용

                // OAuth
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint(
                                (userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(oAuth2UserService)
                        )
                        .successHandler(customSuccessHandler)
                )


                // 요청 경로별 인가
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers(
                                    "/",
                                    "/login", // permit이지만 UsernamePasswordAuthenticationFilter 를 거쳐야 함
                                    "/users",
                                    "/users/email",
                                    "/users/nickname",
                                    "/users/sign-up",
                                    "/auth/refresh-token"
                            ).permitAll()  // 인증없이 요청가능
                            .anyRequest().authenticated();
                })

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

