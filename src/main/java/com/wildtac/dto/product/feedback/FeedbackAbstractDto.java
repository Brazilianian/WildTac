package com.wildtac.dto.product.feedback;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public abstract class FeedbackAbstractDto extends BaseDto {

    @Size(min = 1, message = "The feedback can`t be empty")
    protected String content;

    @Min(value = 0, message = "Mark of feedback can`t be less than 0")
    @Max(value = 5, message = "Mark of feedback can`t be biggest than 5")
    protected byte mark;
}
