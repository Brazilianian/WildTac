package com.wildtac.dto.image;

import com.wildtac.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto extends BaseDto {
    private Long id;

    @NotNull(message = "Content of image can`t be null")
    private String content;

    private Long parentId;
    private int index;
}
