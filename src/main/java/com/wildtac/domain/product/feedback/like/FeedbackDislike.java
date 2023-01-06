package com.wildtac.domain.product.feedback.like;

import com.wildtac.domain.user.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FeedbackDislike extends AbstractFeedbackLikeDislike{

    public FeedbackDislike(User owner) {
        super(owner);
    }

    public FeedbackDislike() {
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
