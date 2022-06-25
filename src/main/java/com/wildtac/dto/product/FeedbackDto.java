package com.wildtac.dto.product;

import com.wildtac.dto.user.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackDto {
//    private UserDto author;
    private Long id;
    private String content;
    private byte mark;
    private int likes;
    private int dislikes;
    private LocalDateTime created;
    private LocalDateTime updated;
}
