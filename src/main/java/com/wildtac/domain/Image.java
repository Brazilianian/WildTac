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
