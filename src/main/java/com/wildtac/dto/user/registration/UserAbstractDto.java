package com.wildtac.dto.user.registration;

import java.time.LocalDateTime;

public abstract class UserAbstractDto {
    private String email;
    private String phoneNumber;
    private LocalDateTime created;
    private LocalDateTime updated;
}
