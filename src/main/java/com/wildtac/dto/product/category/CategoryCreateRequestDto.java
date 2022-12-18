package com.wildtac.dto.product.category;

import com.wildtac.dto.image.ImageDto;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CategoryCreateRequestDto extends CategoryDto {

    @NotNull(message = "Image of category can`t be null")
    private ImageDto image;
}
