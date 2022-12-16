package com.wildtac.service.product;

import com.wildtac.domain.product.feedback.Feedback;
import com.wildtac.domain.product.Product;
import com.wildtac.domain.user.User;
import com.wildtac.exception.FeedbackWasNotFoundException;
import com.wildtac.repository.product.FeedbackRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackService {

    private final ProductService productService;
    private final FeedbackRepo feedbackRepo;

    public Product addFeedbackToProduct(Product product, Feedback feedback, User author) {
        feedback.setAuthor(author);
        product.getFeedbacks().add(feedback);
        return productService.save(product);
    }

    public Feedback likeFeedback(Product product, Long feedbackId, User user) {
        Feedback feedback = product.getFeedbacks()
                .stream()
                .filter(f -> f.getId().equals(feedbackId))
                .findFirst()
                .orElseThrow(() -> new FeedbackWasNotFoundException(String.format("Feedback with id '%s' was not found in product %s", feedbackId, product)));
        feedback.likeFeedback(user);
        return feedbackRepo.save(feedback);
    }

    public Feedback dislikeFeedback(Product product, Long feedbackId, User user) {
        Feedback feedback = product.getFeedbacks()
                .stream()
                .filter(f -> f.getId().equals(feedbackId))
                .findFirst()
                .orElseThrow(() -> new FeedbackWasNotFoundException(String.format("Feedback with id '%s' was not found in product %s", feedbackId, product)));
        feedback.dislikeFeedback(user);
        return feedbackRepo.save(feedback);    }
}
