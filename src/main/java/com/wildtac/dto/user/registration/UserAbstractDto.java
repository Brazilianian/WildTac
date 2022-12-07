package com.wildtac.dto.user.registration;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class UserAbstractDto {
    private String email;
    private String phoneNumber;
    private LocalDateTime created;
    private LocalDateTime updated;

}
