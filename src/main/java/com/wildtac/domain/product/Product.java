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
    private List<Characteristic> characteristics = new ArrayList<>();

    private double discount;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    @Lob
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image")
    private List<String> images = new ArrayList<>();

    private String linkYoutube;
    private String description;
    private double currentCount;
    private double saleCount;

    public Product() {
        super();
    }

    public Product(String name, double cost, List<Characteristic> characteristics, double discount,
                   String phoneNumber, List<Feedback> feedbacks, List<String> images, String linkYoutube,
                   String description, double currentCount, double saleCount) {
        super();

        this.name = name;
        this.cost = cost;
        this.characteristics = characteristics;
        this.discount = discount;
        this.phoneNumber = phoneNumber;
        this.feedbacks = feedbacks;
        this.images = images;
        this.linkYoutube = linkYoutube;
        this.description = description;
        this.currentCount = currentCount;
        this.saleCount = saleCount;
    }
}
