package com.wildtac.domain.product.category;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Subcategory extends BaseEntity {
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Image image;

    @OneToOne(cascade = CascadeType.MERGE)
    private Category category;

    public Subcategory() {
        super();
    }
}
