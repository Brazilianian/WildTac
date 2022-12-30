package com.wildtac.controller;

import com.wildtac.config.security.jwt.JwtAccessTokenHelper;
import com.wildtac.config.security.jwt.JwtRefreshTokenHelper;
import com.wildtac.config.security.jwt.JwtTokenHelper;
import com.wildtac.domain.user.User;
import com.wildtac.dto.user.authentication.UserAuthenticationRequestDto;
import com.wildtac.dto.user.authentication.UserAuthenticationResponseDto;
import com.wildtac.dto.user.registration.UserRegistrationRequestDto;
import com.wildtac.dto.user.registration.UserRegistrationResponseDto;
import com.wildtac.exception.InvalidJwtTokenException;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.user.UserMapper;
import com.wildtac.mapper.user.UserRegstrationMapper;
import com.wildtac.service.UserService;
import com.wildtac.utils.ValidationUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRegstrationMapper userRegstrationMapper;
    private final UserMapper userMapper;
    private final JwtTokenHelper jwtTokenHelper;
    private final JwtAccessTokenHelper jwtAccessTokenHelper;
    private final JwtRefreshTokenHelper jwtRefreshTokenHelper;

    @PostMapping("/registration")
    @ResponseBody
    public UserRegistrationResponseDto registration(@RequestBody @Valid UserRegistrationRequestDto userDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException("Failed to create new user", errors);
        }

        User newUser = userService.createNewUser(userRegstrationMapper.fromDtoToObject(userDto));
        return (UserRegistrationResponseDto) userRegstrationMapper.fromObjectToDto(newUser);
    }

    @PostMapping("/login")
    @ResponseBody
    public UserAuthenticationResponseDto login(@RequestBody UserAuthenticationRequestDto userDto,
                                               HttpServletResponse response) {

        String claims;
        if (userDto.getEmail() != null) {
            claims = userDto.getEmail();
        } else {
            claims = userDto.getPhoneNumber();
        }

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                claims, userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getUserByEmailOrPhoneNumber(claims);

        Cookie jwtRefreshCookie = jwtRefreshTokenHelper.getCookieWithToken(claims);

        user.setRefreshToken(jwtRefreshCookie.getValue());
        user = userService.save(user);

        String jwtAccessToken = jwtAccessTokenHelper.generateAccessToken(claims);

        UserAuthenticationResponseDto userResponseDto = new UserAuthenticationResponseDto
                .UserAuthenticationResponseDtoBuilder()
                .accessToken(jwtAccessToken)
                .user(userMapper.fromObjectToDto(user))
                .build();


        response.addCookie(jwtRefreshCookie);

        return userResponseDto;
    }

    @GetMapping("/refresh")
    @ResponseBody
    public UserAuthenticationResponseDto refreshToken(HttpServletRequest request) {
        String token = jwtRefreshTokenHelper.getTokenFromCookie(request);
        if (token == null) {
            throw new ExpiredJwtException(null, null, "There is no refresh token");
        }
        String claims;
        try {
            claims = jwtTokenHelper.getUsernameFromToken(token);
        } catch (ExpiredJwtException e) {
            User user = userService.getUserByEmailOrPhoneNumber(e.getClaims().getSubject());
            user.setRefreshToken(null);
            userService.save(user);
            throw e;
        }
        User user = userService.getUserByEmailOrPhoneNumber(claims);
        if (user.getRefreshToken() == null) {
            throw new InvalidJwtTokenException("You need to login");
        }
        if (!user.getRefreshToken().equals(token)) {
            throw new InvalidJwtTokenException("Invalid refresh token");
        }

        String accessToken = jwtAccessTokenHelper.generateAccessToken(claims);

        return new UserAuthenticationResponseDto.UserAuthenticationResponseDtoBuilder()
                .accessToken(accessToken)
                .user(userMapper.fromObjectToDto(user))
                .build();
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) {
        String token = jwtRefreshTokenHelper.getTokenFromCookie(request);
        if (token == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new ExpiredJwtException(null, null, "There is no refresh token");
        }

        String claims = jwtTokenHelper.getUsernameFromToken(token);
        User user = userService.getUserByEmailOrPhoneNumber(claims);
        user.setRefreshToken(null);
        userService.save(user);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
