package com.wildtac.dto.product;

import com.wildtac.dto.image.ImageDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductRedactRequestDto extends ProductDto{
    private List<ImageDto> images;
}
