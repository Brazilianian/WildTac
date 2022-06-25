package com.wildtac.dto.product.category;

import com.wildtac.dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubcategoryDto {
    private Long id;
    private ImageDto image;
    private String name;
    private LocalDateTime created;
    private LocalDateTime updated;
}
