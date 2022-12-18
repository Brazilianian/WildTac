package com.wildtac.dto.product.feedback;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public abstract class FeedbackAbstractDto extends BaseDto {

    @Min(value = 1, message = "The feedback can`t be empty")
    protected String content;

    @Min(value = 0, message = "Mark of feedback can`t be less than 0")
    @Max(value = 5, message = "Mark of feedback can`t be biggest than 5")
    protected byte mark;
}
