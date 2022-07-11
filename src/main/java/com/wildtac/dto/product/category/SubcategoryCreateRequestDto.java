package com.wildtac.dto.product.category;

import com.wildtac.dto.image.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubcategoryCreateRequestDto extends SubcategoryDto{
    private ImageDto image;
}
