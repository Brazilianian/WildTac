package com.wildtac.domain.product.feedback.like;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


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
