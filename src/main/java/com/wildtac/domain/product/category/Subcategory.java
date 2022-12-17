package com.wildtac.domain.product.category;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

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

    public Subcategory(String name, Image image) {
        super();

        this.name = name;
        this.image = image;
    }
}
