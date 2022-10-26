package com.wildtac.domain.product;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.Image;
import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Product extends BaseEntity {
    private String name;
    private double cost;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Characteristic> characteristics = new ArrayList<>();

    private double discount;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Image> images = new ArrayList<>();

    private String linkYoutube;

    @Lob
    private String description;


    private double currentCount;
    private double saleCount;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Subcategory subcategory;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Category category;

    public Product() {
        super();
    }

    public int getImageCount() {
        return images.size();
    }
}
