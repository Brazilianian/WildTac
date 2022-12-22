package com.wildtac.config.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

@Component
public class JwtRefreshTokenHelper {
    @Value("${jwt.auth.app}")
    private String appName;
    @Value("${jwt.auth.secret_key}")
    private String secretKey;
    @Value("${jwt.auth.refresh_expires_in}")
    private int refreshTokenExpiresIn;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String COOKIE_REFRESH_TOKEN_NAME = "refreshToken";

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + refreshTokenExpiresIn * 1000L);
    }

    private String generateRefreshToken(String username) {
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secretKey)
                .compact();
    }

    public ResponseCookie getCookieWithToken(String claims) {

        return ResponseCookie.from(COOKIE_REFRESH_TOKEN_NAME, generateRefreshToken(claims))
                .httpOnly(true)
                .maxAge(Duration.ofDays(30))
                .sameSite("None")
                .path("/")
                .build();
    }

    public String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            Cookie cookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals(COOKIE_REFRESH_TOKEN_NAME))
                    .findFirst()
                    .orElse(null);

            if (cookie != null) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
