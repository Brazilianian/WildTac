package com.wildtac.controller;

import com.wildtac.config.security.jwt.JwtTokenHelper;
import com.wildtac.domain.user.User;
import com.wildtac.dto.user.authentication.UserAuthenticationRequestDto;
import com.wildtac.dto.user.authentication.UserAuthenticationResponseDto;
import com.wildtac.dto.user.registration.UserRegistrationRequestDto;
import com.wildtac.dto.user.registration.UserRegistrationResponseDto;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.UserMapper;
import com.wildtac.service.UserService;
import com.wildtac.utils.ValidationUtils;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    public UserRegistrationResponseDto registration(@RequestBody @Valid UserRegistrationRequestDto userDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException("Failed to create new user", errors);
        }

        User newUser = userService.createNewUser(userMapper.fromDtoToObject(userDto));
        return (UserRegistrationResponseDto) userMapper.fromObjectToDto(newUser);
    }

    @PostMapping("/login")
    @ResponseBody
    public UserAuthenticationResponseDto login(@RequestBody UserAuthenticationRequestDto userDto) {

        String claims;
        if (userDto.getEmail() != null) {
            claims = userDto.getEmail();
        } else {
            claims = userDto.getPhoneNumber();
        }

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                claims, userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtTokenHelper.generateToken(user.getUsername());

        UserAuthenticationResponseDto userResponseDto = new UserAuthenticationResponseDto();
        userResponseDto.setToken(jwtToken);

        return userResponseDto;
    }

    @PostMapping("/refresh")
    @ResponseBody
    public UserAuthenticationResponseDto refreshToken(HttpServletRequest request) {
        DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonWebTokenClaims(claims);
        String token = jwtTokenHelper.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());

        return new UserAuthenticationResponseDto(token);
    }

    private Map<String,Object> getMapFromIoJsonWebTokenClaims(DefaultClaims claims) {
        return new HashMap<>(claims);
    }
}
