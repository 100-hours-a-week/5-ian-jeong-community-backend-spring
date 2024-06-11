package com.odop.community.config;

import com.odop.community.auth.JWTFilter;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Duration;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final JWTFilter jwtFilter;

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
                .httpBasic(AbstractHttpConfigurer::disable)  // HTTP Basic 인증을 비활성화
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호 기능을 비활성화
                .formLogin(AbstractHttpConfigurer::disable)  // 폼 기반 로그인을 비활성화(기본 로그인 페이지를 사용하지 않도록 설정)
                .cors(withDefaults()) // CorsConfig 작성내용 적용

                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers(
                                    "/users/sign-in",
                                    "/users/sign-up",
                                    "/users/email",
                                    "/users/nickname",
                                    "/users"
                            ).permitAll()  // 인증없이 요청가능
                            .requestMatchers(HttpMethod.GET, "/users").authenticated()
                            .anyRequest().authenticated();
                })

                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)  // HTTP 요청을 가로채서 JWT 토큰의 유효성을 검사하고 사용자를 인증
                .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 사용 X

        return http.build();
    }
}

