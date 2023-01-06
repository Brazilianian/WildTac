package com.wildtac.domain;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Entity;

@Getter
@Setter
@Entity
@ToString
public class Image extends BaseEntity {
    @Lob
    @ToString.Exclude
    private String content;

    private Long parentId;
    private int index;

    public Image() {
        super();
    }

    public Image(String content, Long parentId) {
        super();

        this.content = content;
        this.parentId = parentId;
    }
}
