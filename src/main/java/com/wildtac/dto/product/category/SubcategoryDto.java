package com.wildtac.dto.product.category;

import com.wildtac.dto.BaseDto;
import lombok.*;

import javax.validation.constraints.Min;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SubcategoryDto extends BaseDto {
    private Long id;

    @Min(value = 3, message = "The name of subcategory must contains at least 3 symbols")
    private String name;
}
