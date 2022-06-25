package com.wildtac.dto.product;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CharacteristicDto {
    private Long id;
    private String name;
    private List<String> values;
    private LocalDateTime created;
    private LocalDateTime updated;
}
