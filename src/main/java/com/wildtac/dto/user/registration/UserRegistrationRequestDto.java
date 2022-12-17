package com.wildtac.dto.user.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequestDto extends UserRegistrationAbstractDto {

    @Min(value = 8, message = "Password must contains at least 8 symbols")
    private String password;
}
