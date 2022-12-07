package com.wildtac.controller;

import com.wildtac.config.security.jwt.JwtTokenHelper;
import com.wildtac.domain.user.User;
import com.wildtac.dto.user.authentication.UserAuthenticationRequestDto;
import com.wildtac.dto.user.authentication.UserAuthenticationResponseDto;
import com.wildtac.dto.user.registration.UserRegistrationRequestDto;
import com.wildtac.dto.user.registration.UserRegistrationResponseDto;
import com.wildtac.mapper.UserMapper;
import com.wildtac.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenHelper jwtTokenHelper;

    @PostMapping("/registration")
    @ResponseBody
    public UserRegistrationResponseDto registration(@RequestBody UserRegistrationRequestDto userDto) {
        User newUser = userService.createNewUser(userMapper.fromDtoToObject(userDto));
        return (UserRegistrationResponseDto) userMapper.fromObjectToDto(newUser);
    }

    @PostMapping("/login")
    @ResponseBody
    public UserAuthenticationResponseDto login(@RequestBody UserAuthenticationRequestDto userDto) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getEmail(), userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtTokenHelper.generateToken(user.getUsername());

        UserAuthenticationResponseDto userResponseDto = new UserAuthenticationResponseDto();
        userResponseDto.setToken(jwtToken);

        return userResponseDto;
    }
}
