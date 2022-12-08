package com.wildtac.service.product;

import com.wildtac.domain.product.Feedback;
import com.wildtac.domain.product.Product;
import com.wildtac.domain.user.User;
import com.wildtac.repository.product.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackService {

    private final ProductRepo productRepo;

    public Product addFeedbackToProduct(Product product, Feedback feedback, User author) {
        feedback.setAuthor(author);
//        product.getFeedbacks().add(feedback);
        return productRepo.save(product);
    }
}
