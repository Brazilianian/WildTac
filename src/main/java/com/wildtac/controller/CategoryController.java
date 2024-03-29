package com.wildtac.controller;

import com.wildtac.domain.product.category.Category;
import com.wildtac.dto.product.category.CategoryCreateRequestDto;
import com.wildtac.dto.product.category.CategoryDto;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.product.category.CategoryMapper;
import com.wildtac.service.product.category.CategoryService;
import com.wildtac.utils.ValidationUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    public CategoryController(CategoryMapper categoryMapper, CategoryService categoryService) {
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return categoryMapper.fromObjectListToDtoList(categories);
    }

    @GetMapping("/{categoryId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryByName(@PathVariable(name = "categoryId") Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);

        return categoryMapper.fromObjectToDto(category);
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('category:write')")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CategoryCreateRequestDto category,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException("Failed to create new category", errors);
        }

        Category createdCategory = categoryService.createCategory(categoryMapper.fromDtoToObject(category));

        return categoryMapper.fromObjectToDto(createdCategory);
    }

    @PutMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('category:redact')")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto redactCategory(@RequestBody @Valid CategoryCreateRequestDto category,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException(String.format("Failed to redact category with id '%s'", category.getId()), errors);
        }

        Category updatedCategory = categoryService.redactCategory(categoryMapper.fromDtoToObject(category));

        return categoryMapper.fromObjectToDto(updatedCategory);
    }
}

