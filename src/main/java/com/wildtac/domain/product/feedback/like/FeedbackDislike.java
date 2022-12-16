package com.wildtac.domain.product.feedback.like;

import com.wildtac.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@Entity
public class FeedbackDislike extends AbstractFeedbackLikeDislike{

    public FeedbackDislike(User owner) {
        super(owner);
    }

    public FeedbackDislike() {
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
