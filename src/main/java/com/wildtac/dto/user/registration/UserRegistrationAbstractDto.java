package com.wildtac.dto.user.registration;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;

@Getter
@Setter
public abstract class UserRegistrationAbstractDto extends BaseDto {

    @Email(message = "Invalid email address")
    protected String email;

    // TODO: 18.12.2022 add pattern to phone number
    protected String phoneNumber;
}
