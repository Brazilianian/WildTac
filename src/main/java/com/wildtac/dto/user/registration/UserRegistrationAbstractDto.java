package com.wildtac.dto.user.registration;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class UserRegistrationAbstractDto extends BaseDto {
    protected String email;
    protected String phoneNumber;
}
