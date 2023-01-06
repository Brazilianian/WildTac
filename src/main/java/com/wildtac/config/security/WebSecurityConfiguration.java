package com.wildtac.config.security;

import com.wildtac.config.security.jwt.JwtTokenHelper;
import com.wildtac.config.security.jwt.RestAuthenticationEntryPoint;
import com.wildtac.service.security.UserSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

//    private final UserSecurityService userSecurityService;
//    private final RestAuthenticationEntryPoint authenticationEntryPoint;
//    private final JwtTokenHelper jwtTokenHelper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .antMatchers("/api/v1/**")
//                .antMatchers("/api/v1/auth/*", "/api/v1/product/*", "/api/v1/category/*", "/api/v1/image/*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(userSecurityService, jwtTokenHelper), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(authConfig -> {
           authConfig.requestMatchers("/api/v1/auth/*", "/api/v1/product/*", "/api/v1/category/*", "/api/v1/image/*").permitAll();
           authConfig.anyRequest().authenticated();
        });

        http.cors();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nullable CorsRegistry registry) {
                assert registry != null;
                registry
                        .addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins("http://10.5.113.113:3000", "http://localhost:3000")
                        .allowedMethods("DELETE", "POST", "GET", "OPTIONS, PATCH, HEAD, TRACE")
                        .allowedHeaders("*");
            }
        };
    }
}
