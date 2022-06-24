package com.wildtac.domain.product;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString
public class Feedback extends BaseEntity {

    @OneToOne(cascade = CascadeType.MERGE)
    private User author;

    @Lob
    private String content;

    private byte mark;
    private int likes;
    private int dislikes;

    public Feedback() {
        super();
    }

    public Feedback(User author, String content, byte mark, int likes, int dislikes) {
        super();

        this.author = author;
        this.content = content;
        this.mark = mark;
        this.likes = likes;
        this.dislikes = dislikes;
    }
}
