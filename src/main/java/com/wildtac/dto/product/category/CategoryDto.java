package com.wildtac.dto.product.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
    private Long id;
    private String name;
    private List<SubcategoryDto> subcategories = new ArrayList<>();
    private LocalDateTime created;
    private LocalDateTime updated;
}
