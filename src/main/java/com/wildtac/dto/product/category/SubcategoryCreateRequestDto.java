package com.wildtac.dto.product.category;

import com.wildtac.dto.image.ImageDto;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SubcategoryCreateRequestDto extends SubcategoryDto {

    @NotNull(message = "Image of subcategory can`t be null")
    private ImageDto image;
}
