package com.wildtac.domain.product;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.Image;
import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.domain.product.feedback.Feedback;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
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
    @Transient
    private List<Image> images = new ArrayList<>();

    @ToString.Exclude
    private String linkYoutube;

    @Lob
    @ToString.Exclude
    private String description;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private double currentCount;

    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private double saleCount;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Subcategory subcategory;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Category category;

    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private int visitCount;

    public Product() {
        super();
    }

    public int getImageCount() {
        return images.size();
    }

    public void visitCountIncrement() {
        visitCount++;
    }
}
