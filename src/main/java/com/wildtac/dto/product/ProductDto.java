package com.wildtac.dto.product;

import com.wildtac.dto.BaseDto;
import com.wildtac.dto.product.feedback.FeedbackDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@ToString
@Getter
@Setter
public class ProductDto extends BaseDto {
    private Long id;

    @Size(min = 3, message = "Name of product must contains at least 3 symbols")
    private String name;

    @Positive(message = "The price of product can`t be less than zero")
    private double cost;

    private List<CharacteristicDto> characteristics;

    @Min(value = 1, message = "The discount of product can`t be less or equals zero")
    private double discount;

    private List<FeedbackDto> feedbacks;
    private String linkYoutube;
    private String description;

    @Min(value = 0, message = "Count of product can`t be less than zero")
    private double currentCount;

    private double saleCount;
    private Long subcategoryId;
    private Long categoryId;
    private int imageCount;
    private int visitCount;
}
