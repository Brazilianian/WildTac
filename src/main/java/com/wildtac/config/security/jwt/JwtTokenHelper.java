package com.wildtac.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenHelper {
    @Value("${jwt.auth.app}")
    private String appName;

    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    @Value("${jwt.auth.expires_in}")
    private int tokenExpiresIn;

    @Value("${jwt.auth.refresh_expires_in}")
    private int refreshTokenExpiresIn;

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        String username;
        final Claims claims = this.getAllClaimsFromToken(token);
        username = claims.getSubject();
        return username;
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secretKey)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + tokenExpiresIn * 1000L);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        Date expireDate;
        try {
            expireDate = getExpirationDate(token);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return expireDate.before(new Date());
    }

    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getToken(HttpServletRequest request) {

        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean isRefresh(HttpServletRequest request) {
        String isRefreshHeader = request.getHeader("IsRefresh");
        StringBuffer requestURL = request.getRequestURL();
        return (Boolean.parseBoolean(isRefreshHeader) && requestURL.toString().contains("/api/v1/auth/refresh"));
    }

    public String generateRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiresIn))
                .signWith(SIGNATURE_ALGORITHM, secretKey).compact();
    }
}
