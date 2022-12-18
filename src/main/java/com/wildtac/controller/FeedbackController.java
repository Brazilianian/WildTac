package com.wildtac.controller;

import com.wildtac.domain.product.Product;
import com.wildtac.domain.product.feedback.Feedback;
import com.wildtac.domain.user.User;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.dto.product.feedback.FeedbackCreateRequestDto;
import com.wildtac.dto.product.feedback.FeedbackDto;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.product.FeedbackMapper;
import com.wildtac.mapper.product.ProductMapper;
import com.wildtac.service.product.FeedbackService;
import com.wildtac.service.product.ProductService;
import com.wildtac.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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
    @PreAuthorize("hasAuthority('feedback:write')")
    public ProductDto sendFeedback(@PathVariable Long productId,
                                   @AuthenticationPrincipal User user,
                                   @RequestBody @Valid FeedbackCreateRequestDto feedbackDto,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException(String.format("Failed to send feedback to the product with id '%s' by user '%s'", productId, user), errors);
        }
        Product product = productService.getProductById(productId);
        product = feedbackService.addFeedbackToProduct(product, feedbackMapper.fromDtoToObject(feedbackDto), user);
        return productMapper.fromObjectToDto(product);
    }

    @PostMapping("/feedback/{feedbackId}/like")
    @ResponseBody
    @PreAuthorize("hasAuthority('feedback:like')")
    public FeedbackDto likeFeedback(@AuthenticationPrincipal User user,
                                   @PathVariable Long feedbackId,
                                   @PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        Feedback feedback = feedbackService.likeFeedback(product, feedbackId, user);
        return feedbackMapper.fromObjectToDto(feedback);
    }

    @PostMapping("/feedback/{feedbackId}/dislike")
    @ResponseBody
    @PreAuthorize("hasAuthority('feedback:like')")
    public FeedbackDto dislikeFeedback(@AuthenticationPrincipal User user,
                                       @PathVariable Long feedbackId,
                                       @PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        Feedback feedback = feedbackService.dislikeFeedback(product, feedbackId, user);
        return feedbackMapper.fromObjectToDto(feedback);
    }

}
