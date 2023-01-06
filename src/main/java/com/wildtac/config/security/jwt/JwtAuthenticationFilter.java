package com.wildtac.config.security.jwt;

import com.wildtac.service.security.UserSecurityService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserSecurityService userSecurityService;
    private final JwtTokenHelper jwtTokenHelper;

    public JwtAuthenticationFilter(UserSecurityService userSecurityService, JwtTokenHelper jwtTokenHelper) {
        this.userSecurityService = userSecurityService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authToken = jwtTokenHelper.getToken(request);

        if (authToken != null && !authToken.equals("null")) {
            try {
                String username = jwtTokenHelper.getUsernameFromToken(authToken);

                if (username != null) {

                    UserDetails userDetails = userSecurityService.loadUserByUsername(username);

                    if (jwtTokenHelper.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                }
            } catch (ExpiredJwtException ex) {
                if (jwtTokenHelper.isRefresh(request)) {
                    allowForRefreshToken(ex, request);
                } else {
                    request.setAttribute("exception", ex);
                    response.setHeader("IsExpired", "true");
                }
            }

        }

        filterChain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("claims", ex.getClaims());
    }
}
