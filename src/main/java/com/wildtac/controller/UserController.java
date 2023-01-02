package com.wildtac.controller;

import com.wildtac.config.security.jwt.JwtTokenHelper;
import com.wildtac.domain.user.User;
import com.wildtac.dto.user.UserDto;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.user.UserMapper;
import com.wildtac.service.UserService;
import com.wildtac.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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

    @ResponseBody
    @PreAuthorize("hasAuthority('user:read')")
    @GetMapping("/users")
    public List<UserDto> getPageOfUsers(Pageable pageable) {
        List<User> users = userService.getPageOfUsers(pageable);
        return userMapper.fromObjectListToDtoList(users);
    }

    @ResponseBody
    @PutMapping("/profile")
    public UserDto redactUser(@RequestBody @Valid UserDto userDto,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Failed to redact profile info", ValidationUtils.getErrors(bindingResult));
        }

        User redactedUser = userService.redactUserInfo(user, userDto);
        return userMapper.fromObjectToDto(redactedUser);
    }
}
