package com.wildtac.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Getter
@Setter
@Entity
@ToString
public class Image extends BaseEntity {
    @Lob
    private String content;

    public Image() {
        super();
    }

    public Image(String content) {
        super();
        this.content = content;
    }
}
