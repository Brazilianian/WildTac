package com.wildtac.domain.product;

import com.wildtac.domain.BaseEntity;
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

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Characteristic> characteristics = new ArrayList<>();

    private double discount;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Feedback> feedbacks = new ArrayList<>();

    @ElementCollection
    private List<String> images = new ArrayList<>();

    private String linkYoutube;
    private String description;
    private double currentCount;
    private double saleCount;

    public Product() {
        super();
    }

    public Product(String name, double cost, List<Characteristic> characteristics, double discount,
                   List<Feedback> feedbacks, List<String> images, String linkYoutube,
                   String description, double currentCount, double saleCount) {
        super();

        this.name = name;
        this.cost = cost;
        this.characteristics = characteristics;
        this.discount = discount;
        this.feedbacks = feedbacks;
        this.images = images;
        this.linkYoutube = linkYoutube;
        this.description = description;
        this.currentCount = currentCount;
        this.saleCount = saleCount;
    }
}
