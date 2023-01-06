package com.wildtac.domain.product.category;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Category extends BaseEntity {
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Subcategory> subcategories = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Image image;

    public Category() {
        super();
    }
}
