package com.wildtac.controller;

import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.dto.product.category.SubcategoryDto;
import com.wildtac.mapper.product.category.SubcategoryMapper;
import com.wildtac.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category/{categoryId}/subcategory")
public class SubcategoryController {

    private final CategoryService categoryService;
    private final SubcategoryMapper subcategoryMapper;

    public SubcategoryController(CategoryService categoryService, SubcategoryMapper subcategoryMapper) {
        this.categoryService = categoryService;
        this.subcategoryMapper = subcategoryMapper;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SubcategoryDto createSubcategory(@PathVariable(name = "categoryId") Long categoryId,
                                            @RequestBody SubcategoryDto subcategoryDto) {

        Category category = categoryService.getCategoryById(categoryId);

        Subcategory createdSubcategory = categoryService.addSubcategoryOfCategory(category, subcategoryMapper.fromDtoToObject(subcategoryDto));

        return subcategoryMapper.fromObjectToDto(createdSubcategory);
    }
}
