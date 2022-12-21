package com.wildtac.config.security;

import com.wildtac.config.security.jwt.JwtAuthenticationFilter;
import com.wildtac.config.security.jwt.JwtTokenHelper;
import com.wildtac.config.security.jwt.RestAuthenticationEntryPoint;
import com.wildtac.service.security.UserSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity

@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private final UserSecurityService userSecurityService;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtTokenHelper jwtTokenHelper;

    private final List<String> allowedOrigins = new ArrayList<>(){{
        // FIXME: 21.12.2022 КОСТИЛЬ
        add("http://192.168.219.173:3000/");
//        add("*");
    }};
    private final List<String> allowedHeaders = new ArrayList<>(){{
        add("*");
    }};
    private final List<String> exposedHeaders = new ArrayList<>(){{
        add("*");
    }};
    private final List<String> allowedMethods = new ArrayList<>(){{
        add("GET");
        add("POST");
        add("DELETE");
        add("PUT");
        add("OPTIONS");
    }};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
//                .configurationSource(request -> {
//                    CorsConfiguration cors = new CorsConfiguration();
//                    cors.setAllowCredentials(true);
//                    cors.setAllowedOrigins(this.allowedOrigins);
//                    cors.setAllowedHeaders(this.allowedHeaders);
//                    cors.setAllowedMethods(this.allowedMethods);
//                    return cors;
//                })
                .disable();

        http
                .csrf().disable();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .antMatcher("/api/v1/**").authorizeRequests()
                .antMatchers("/api/v1/auth/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(userSecurityService, jwtTokenHelper), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
