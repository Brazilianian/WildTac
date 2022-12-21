package com.wildtac.dto.user.authentication;

import com.wildtac.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationResponseDto {
    private String accessToken;
    private String refreshToken;

}
