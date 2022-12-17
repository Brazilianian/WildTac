package com.wildtac.domain.product.feedback;

import com.wildtac.domain.BaseEntity;
import com.wildtac.domain.product.feedback.like.AbstractFeedbackLikeDislike;
import com.wildtac.domain.product.feedback.like.FeedbackDislike;
import com.wildtac.domain.product.feedback.like.FeedbackLike;
import com.wildtac.domain.user.User;
import lombok.AccessLevel;
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
public class Feedback extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private User author;

    @Lob
    private String content;

    private byte mark;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FeedbackLike> likes = new ArrayList<>();

    @Getter
    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FeedbackDislike> dislikes = new ArrayList<>();

    public Feedback() {
        super();
    }

    public void likeFeedback(User user) {
        FeedbackLike like = (FeedbackLike) likeDislikeFeedback(likes, dislikes, user);
        if (like == null) {
            likes.add(new FeedbackLike(user));
        } else {
            likes.remove(like);
        }
    }

    public void dislikeFeedback(User user) {
        FeedbackDislike dislike = (FeedbackDislike) likeDislikeFeedback(dislikes, likes, user);
        if (dislike == null) {
            dislikes.add(new FeedbackDislike(user));
        } else {
            dislikes.remove(dislike);
        }
    }

    private AbstractFeedbackLikeDislike likeDislikeFeedback(List<? extends AbstractFeedbackLikeDislike> likeDislikesListToAdd,
                                     List<? extends AbstractFeedbackLikeDislike> likeDislikeListToRemove,
                                     User user) {
        likeDislikeListToRemove
                .stream()
                .filter(toRemove -> toRemove.getOwner().getId().equals(user.getId()))
                .findFirst()
                .ifPresent(likeDislikeListToRemove::remove);

        return likeDislikesListToAdd
                .stream()
                .filter(ul -> ul.getOwner().getId().equals(user.getId()))
                .findFirst().orElse(null);
    }
}
