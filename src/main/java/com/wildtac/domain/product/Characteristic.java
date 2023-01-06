package com.wildtac.domain.product;

import com.wildtac.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@ToString
public class Characteristic extends BaseEntity {

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> values = new ArrayList<>();

    public Characteristic() {
        super();
    }
}
