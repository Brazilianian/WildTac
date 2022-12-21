package com.wildtac.dto.user;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private String name;
    private String surname;
    private String address;
    private String role;
}
