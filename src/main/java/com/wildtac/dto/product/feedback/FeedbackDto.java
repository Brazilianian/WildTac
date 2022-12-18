package com.wildtac.dto.product.feedback;

import com.wildtac.dto.product.feedback.like.LikeDislikeDto;
import com.wildtac.dto.user.FeedbackAuthorDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedbackDto extends FeedbackAbstractDto{
    private Long id;
    private FeedbackAuthorDto author;
    private List<LikeDislikeDto> likes;
    private List<LikeDislikeDto> dislikes;
}
