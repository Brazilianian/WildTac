package com.wildtac.controller;

import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.dto.product.category.SubcategoryCreateRequestDto;
import com.wildtac.dto.product.category.SubcategoryDto;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.product.category.SubcategoryMapper;
import com.wildtac.service.product.category.CategoryService;
import com.wildtac.service.product.category.SubcategoryService;
import com.wildtac.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/category/{categoryId}/subcategory")
@AllArgsConstructor
public class SubcategoryController {

    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final SubcategoryMapper subcategoryMapper;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('category:write')")
    public SubcategoryDto createSubcategory(@PathVariable(name = "categoryId") Long categoryId,
                                            @RequestBody @Valid SubcategoryCreateRequestDto subcategoryDto,
                                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException(String.format("Failed to create new subcategory of category with id '%s'", categoryId), errors);
        }
        Category category = categoryService.getCategoryById(categoryId);

        Subcategory createdSubcategory = subcategoryService.addSubcategoryOfCategory(category, subcategoryMapper.fromDtoToObject(subcategoryDto));

        return subcategoryMapper.fromObjectToDto(createdSubcategory);
    }
}
