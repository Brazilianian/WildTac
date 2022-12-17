package com.wildtac.service.product;

import com.wildtac.domain.product.feedback.Feedback;
import com.wildtac.domain.product.Product;
import com.wildtac.domain.user.User;
import com.wildtac.exception.wasnotfound.FeedbackWasNotFoundException;
import com.wildtac.repository.product.FeedbackRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class FeedbackService {

    private final ProductService productService;
    private final FeedbackRepo feedbackRepo;

    /**
     * The method adds new feedback to product
     * @param product - the product in which feedback will be added
     * @param feedback - new feedback
     * @param author - the user that have sent feedback. He is author of feedback
     * @return Product - Product with added new feedback
     */
    public Product addFeedbackToProduct(Product product, Feedback feedback, User author) {
        feedback.setAuthor(author);
        product.getFeedbacks().add(feedback);

        Product savedProduct = productService.save(product);
        log.info(String.format("Feedback '%s' was added to product '%s' by the user '%s'", feedback, product, author));
        return savedProduct;
    }

    /**
     * The method changes counts of likes of feedback (where like - user)
     * If current user have disliked this feedback before, then dislike will be removed and new like will be added to feedback.
     * If current user have liked this feedback before, then like will be removed from feedback.
     * In other case new like will be added to feedback
     * @param product - the product that contains feedback
     * @param feedbackId - the field of feedback
     * @param user - the author of feedback
     * @return Feedback - feedback with changed counts of likes
     * @throws FeedbackWasNotFoundException - if feedback with selected id was not found in product
     */
    public Feedback likeFeedback(Product product, Long feedbackId, User user) {
        Feedback feedback = product.getFeedbacks()
                .stream()
                .filter(f -> f.getId().equals(feedbackId))
                .findFirst()
                .orElseThrow(() -> new FeedbackWasNotFoundException(String.format("Feedback with id '%s' was not found in product %s", feedbackId, product)));
        feedback.likeFeedback(user);

        Feedback savedFeedback = feedbackRepo.save(feedback);
        log.info(String.format("User '%s' liked feedback '%s'", user, feedback));
        return savedFeedback;
    }

    /**
     * The logic of current method is the same as in method likeFeedback()
     * Current method changes counts of dislikes (where dislike - user)
     * If current user have liked this feedback before, then like will be removed and new dislike will be added to feedback.
     * If current user have disliked this feedback before, then dislike will be removed from feedback.
     * In other case new dislike will be added to feedback
     * @param product - the product that contains feedback
     * @param feedbackId - the field of feedback
     * @param user - the author of feedback
     * @return Feedback - feedback with changed counts of dislikes
     * @throws FeedbackWasNotFoundException - if feedback with selected id was not found in product
     */
    public Feedback dislikeFeedback(Product product, Long feedbackId, User user) {
        Feedback feedback = product.getFeedbacks()
                .stream()
                .filter(f -> f.getId().equals(feedbackId))
                .findFirst()
                .orElseThrow(() -> new FeedbackWasNotFoundException(String.format("Feedback with id '%s' was not found in product %s", feedbackId, product)));
        feedback.dislikeFeedback(user);

        Feedback savedFeedback = feedbackRepo.save(feedback);
        log.info(String.format("User '%s' disliked feedback '%s'", user, feedback));
        return savedFeedback;
    }
}
