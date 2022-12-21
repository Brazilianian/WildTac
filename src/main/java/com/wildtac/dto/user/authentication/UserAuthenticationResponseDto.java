package com.wildtac.dto.user.authentication;

import com.wildtac.dto.user.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthenticationResponseDto {
    private String accessToken;
    private UserDto user;

    public static class UserAuthenticationResponseDtoBuilder{

        private String accessToken;
        private UserDto user;

        public UserAuthenticationResponseDtoBuilder() {}

        public UserAuthenticationResponseDtoBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public UserAuthenticationResponseDtoBuilder user(UserDto userDto) {
            this.user = userDto;
            return this;
        }

        public UserAuthenticationResponseDto build() {
            return new UserAuthenticationResponseDto(this);
        }
    }

    private UserAuthenticationResponseDto (UserAuthenticationResponseDtoBuilder builder) {
        accessToken = builder.accessToken;
        user = builder.user;
    }
}
