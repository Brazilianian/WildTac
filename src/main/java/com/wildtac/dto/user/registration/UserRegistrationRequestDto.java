package com.wildtac.dto.user.registration;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequestDto extends UserRegistrationAbstractDto {

    @Size(min = 8, message = "Password must contains at least 8 symbols")
    private String password;
}
