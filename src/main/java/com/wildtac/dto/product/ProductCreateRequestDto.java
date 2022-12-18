package com.wildtac.dto.product;

import com.wildtac.dto.image.ImageDto;
import lombok.*;

import java.util.List;


@ToString
@Getter
@Setter
public class ProductCreateRequestDto extends ProductDto{
    private List<ImageDto> images;
}
