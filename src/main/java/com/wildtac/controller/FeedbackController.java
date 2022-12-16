package com.wildtac.controller;

import com.wildtac.domain.product.Product;
import com.wildtac.domain.product.feedback.Feedback;
import com.wildtac.domain.user.User;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.dto.product.feedback.FeedbackCreateRequestDto;
import com.wildtac.dto.product.feedback.FeedbackDto;
import com.wildtac.mapper.product.FeedbackMapper;
import com.wildtac.mapper.product.ProductMapper;
import com.wildtac.service.product.FeedbackService;
import com.wildtac.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product/{productId}/")
public class FeedbackController {

    private final ProductService productService;
    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;
    private final ProductMapper productMapper;

    @PostMapping("/feedback")
    @ResponseBody
    public ProductDto sendFeedback(@RequestBody FeedbackCreateRequestDto feedbackDto,
                                   @PathVariable Long productId,
                                   @AuthenticationPrincipal User user) {
        Product product = productService.getProductById(productId);
        product = feedbackService.addFeedbackToProduct(product, feedbackMapper.fromDtoToObject(feedbackDto), user);
        return productMapper.fromObjectToDto(product);
    }

    @PostMapping("/feedback/{feedbackId}/like")
    @ResponseBody
    public FeedbackDto likeFeedback(@AuthenticationPrincipal User user,
                                   @PathVariable Long feedbackId,
                                   @PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        Feedback feedback = feedbackService.likeFeedback(product, feedbackId, user);
        return feedbackMapper.fromObjectToDto(feedback);
    }

    @PostMapping("/feedback/{feedbackId}/dislike")
    @ResponseBody
    public FeedbackDto dislikeFeedback(@AuthenticationPrincipal User user,
                                       @PathVariable Long feedbackId,
                                       @PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        Feedback feedback = feedbackService.dislikeFeedback(product, feedbackId, user);
        return feedbackMapper.fromObjectToDto(feedback);
    }

}
