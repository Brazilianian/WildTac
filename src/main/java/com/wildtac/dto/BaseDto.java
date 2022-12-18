package com.wildtac.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDto {
    protected LocalDateTime created;
    protected LocalDateTime updated;
}
