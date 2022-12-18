package com.wildtac.controller;

import com.wildtac.domain.user.User;
import com.wildtac.dto.user.UserDto;
import com.wildtac.mapper.user.UserMapper;
import com.wildtac.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profile")
    public UserDto getUserProfile() {
        String claims = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmailOrPhoneNumber(claims);
        return userMapper.fromObjectToDto(user);
    }
}
