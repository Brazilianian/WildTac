package com.wildtac.dto.product.feedback;

import com.wildtac.dto.user.FeedbackAuthorDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDto extends FeedbackAbstractDto{
    private Long id;
    private FeedbackAuthorDto author;
}
