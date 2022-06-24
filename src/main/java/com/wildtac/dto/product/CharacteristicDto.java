package com.wildtac.dto.product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CharacteristicDto {
    private String name;
    private List<String> values;
}
