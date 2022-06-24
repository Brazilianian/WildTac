package com.wildtac.dto.product;

import com.wildtac.dto.user.UserDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackDto {
//    private UserDto author;
    private String content;
    private byte mark;
    private int likes;
    private int dislikes;
}
