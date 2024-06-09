package com.odop.community.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    private static Long EXPIRED_TIME = 1000 * 60 * 60 * 24L;
    private static String SECRET = "testSecretKey20230327testSecretKey20230327testSecretKey20230327";

    private SecretKey secretKey = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());

    public String getNickname(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public boolean isExpired(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String nickname) {
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()

                .claim("nickname", nickname)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRED_TIME))
                .signWith(secretKey)
                .compact();
    }
}
