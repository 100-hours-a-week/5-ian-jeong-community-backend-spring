package com.odop.community.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@SpringBootTest
public class JwtTest {
    private String secret = "";
    private SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testCreateAndParseToken() {
        Long userId = 123L;
        String token = createToken(userId);

        Assertions.assertThatThrownBy(() -> jwtUtil.isExpired(token))
                .isInstanceOf(ExpiredJwtException.class);
    }

    private String createToken(Long id) {
        return "Bearer " + Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()

                .claim("id", id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()))
                .signWith(secretKey)
                .compact();
    }
}
