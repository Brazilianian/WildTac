package com.wildtac.domain.product.feedback.like;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractFeedbackLikeDislike extends BaseEntity {
    @OneToOne(cascade = CascadeType.MERGE)
    protected User owner;

    public AbstractFeedbackLikeDislike(User owner) {
        super();
        this.owner = owner;
    }

    public AbstractFeedbackLikeDislike() {
        super();
    }
}
