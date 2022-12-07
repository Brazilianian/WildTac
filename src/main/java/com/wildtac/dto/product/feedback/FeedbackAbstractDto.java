package com.wildtac.dto.product.feedback;

import com.wildtac.dto.BaseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class FeedbackAbstractDto extends BaseDto {
    protected Long id;
    protected String content;
    protected byte mark;
    protected int likes;
    protected int dislikes;
}
