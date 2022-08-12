package com.wildtac.domain.product;

import com.wildtac.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    public Characteristic(String name, List<String> values) {
        super();
        this.name = name;
        this.values = values;
    }
}
