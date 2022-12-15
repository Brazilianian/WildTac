package com.wildtac.dto.product.feedback;

import com.wildtac.dto.BaseDto;
import lombok.*;

@Getter
@Setter
public abstract class FeedbackAbstractDto extends BaseDto {
    protected String content;
    protected byte mark;
    protected int likes;
    protected int dislikes;
}
