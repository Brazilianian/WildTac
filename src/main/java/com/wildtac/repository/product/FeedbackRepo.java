package com.wildtac.repository.product;

import com.wildtac.domain.product.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback, Long> {
}
