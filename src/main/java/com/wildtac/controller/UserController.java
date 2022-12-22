package com.wildtac.controller;

import com.wildtac.config.security.jwt.JwtTokenHelper;
import com.wildtac.domain.user.User;
import com.wildtac.dto.user.UserDto;
import com.wildtac.mapper.user.UserMapper;
import com.wildtac.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtTokenHelper jwtTokenHelper;

    @ResponseBody
    @GetMapping("/profile")
    public UserDto getUserProfile(HttpServletRequest request) {
        String token = jwtTokenHelper.getToken(request);
        String claims = jwtTokenHelper.getUsernameFromToken(token);
        User user = userService.getUserByEmailOrPhoneNumber(claims);
        return userMapper.fromObjectToDto(user);
    }
}
