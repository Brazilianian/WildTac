package com.wildtac.dto.product;

import com.wildtac.dto.ImageDto;
import com.wildtac.dto.product.category.SubcategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private double cost;
    private List<CharacteristicDto> characteristics;
    private double discount;
    private List<FeedbackDto> feedbacks;
    private List<ImageDto> images;
    private String linkYoutube;
    private String description;
    private double currentCount;
    private double saleCount;
    private Long subcategoryId;
    private LocalDateTime created;
    private LocalDateTime updated;
}
