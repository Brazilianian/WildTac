package com.wildtac.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDto {
    // TODO: 06.12.2022 complete inheritance
    protected LocalDateTime created;
    protected LocalDateTime updated;
}
