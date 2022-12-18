package com.wildtac.dto.product;

import com.wildtac.dto.BaseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CharacteristicDto extends BaseDto {
    private Long id;
    private String name;
    private List<String> values;
}
