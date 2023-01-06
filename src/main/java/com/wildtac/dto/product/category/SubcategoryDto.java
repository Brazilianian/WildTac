package com.wildtac.dto.product.category;

import com.wildtac.dto.BaseDto;
import jakarta.validation.constraints.Size;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SubcategoryDto extends BaseDto {
    private Long id;

    @Size(min = 3, message = "The name of subcategory must contains at least 3 symbols")
    private String name;
}
