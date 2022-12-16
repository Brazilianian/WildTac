package com.wildtac.dto.product.feedback.like;

import com.wildtac.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDislikeDto extends BaseDto {
    private LikeDislikeOwnerDto owner;
}
