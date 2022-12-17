package com.wildtac.controller;

import com.wildtac.domain.product.category.Category;
import com.wildtac.dto.product.category.CategoryCreateRequestDto;
import com.wildtac.dto.product.category.CategoryDto;
import com.wildtac.mapper.product.category.CategoryMapper;
import com.wildtac.service.product.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('category:read')")
    public List<CategoryDto> getwAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return categoryMapper.fromObjectListToDtoList(categories);
    }

    @GetMapping("/{categoryId}")
    @ResponseBody
    @PreAuthorize("hasAuthority('category:read')")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryByName(@PathVariable(name = "categoryId") Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);

        return categoryMapper.fromObjectToDto(category);
    }

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('category:write')")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody CategoryCreateRequestDto category) {
        Category createdCategory = categoryService.createCategory(categoryMapper.fromDtoToObject(category));

        return categoryMapper.fromObjectToDto(createdCategory);
    }

    @PutMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('category:redact')")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto redactCategory(@RequestBody CategoryCreateRequestDto category) {
        Category updatedCategory = categoryService.redactCategory(categoryMapper.fromDtoToObject(category));

        return categoryMapper.fromObjectToDto(updatedCategory);
    }
}

