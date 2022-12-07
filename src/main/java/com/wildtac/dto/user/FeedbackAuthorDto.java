package com.wildtac.dto.user;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackAuthorDto extends BaseDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
}
