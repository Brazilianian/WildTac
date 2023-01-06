package com.wildtac.domain.product.feedback.like;

import com.wildtac.domain.user.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FeedbackLike extends AbstractFeedbackLikeDislike {

    public FeedbackLike(User owner) {
        super(owner);
    }

    public FeedbackLike() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
