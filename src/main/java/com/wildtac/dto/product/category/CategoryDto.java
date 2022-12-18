package com.wildtac.dto.product.category;

import com.wildtac.dto.BaseDto;
import lombok.*;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CategoryDto extends BaseDto {
    private Long id;

    @Min(value = 3, message = "The name of category must contains at least 3 symbols")
    private String name;

    private List<SubcategoryDto> subcategories = new ArrayList<>();
}
