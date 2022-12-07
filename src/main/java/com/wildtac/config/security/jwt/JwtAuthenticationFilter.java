package com.wildtac.config.security.jwt;

import com.wildtac.service.security.UserSecurityService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        if (authToken != null) {

            if (jwtTokenHelper.isTokenExpired(authToken)) {
                response.setHeader("IsExpired", "true");
                if (!jwtTokenHelper.isRefresh(request)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            String username = jwtTokenHelper.getUsernameFromToken(authToken);

            if (username != null) {

                UserDetails userDetails = userSecurityService.loadUserByUsername(username);

                if (jwtTokenHelper.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }

        }

        filterChain.doFilter(request, response);
    }
}
