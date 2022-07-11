package com.wildtac.dto.product;

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
    private String linkYoutube;
    private String description;
    private double currentCount;
    private double saleCount;
    private Long subcategoryId;
    private Long categoryId;
    private LocalDateTime created;
    private LocalDateTime updated;
}
