package com.wildtac.dto.user.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequestDto extends UserRegistrationAbstractDto {

    @Size(min = 8, message = "Password must contains at least 8 symbols")
    private String password;
}
