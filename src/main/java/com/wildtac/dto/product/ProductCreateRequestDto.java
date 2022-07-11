package com.wildtac.dto.product;

import com.wildtac.dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCreateRequestDto extends ProductDto{
    private List<ImageDto> images;
}
