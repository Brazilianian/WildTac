package com.wildtac.dto.user.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.config.web.servlet.SecurityMarker;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequestDto {
    private String phoneNumber;
    private String email;
    private String password;
}
