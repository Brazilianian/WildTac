package com.wildtac.dto.user.registration;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public abstract class UserRegistrationAbstractDto extends BaseDto {

    @Email
    protected String email;

    protected String phoneNumber;
}
