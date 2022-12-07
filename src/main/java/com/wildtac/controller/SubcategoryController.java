package com.wildtac.controller;

import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.dto.product.category.SubcategoryCreateRequestDto;
import com.wildtac.dto.product.category.SubcategoryDto;
import com.wildtac.mapper.product.category.SubcategoryMapper;
import com.wildtac.service.product.category.CategoryService;
import com.wildtac.service.product.category.SubcategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public SubcategoryDto createSubcategory(@PathVariable(name = "categoryId") Long categoryId,
                                            @RequestBody SubcategoryCreateRequestDto subcategoryDto) {

        Category category = categoryService.getCategoryById(categoryId);

        Subcategory createdSubcategory = subcategoryService.addSubcategoryOfCategory(category, subcategoryMapper.fromDtoToObject(subcategoryDto));

        return subcategoryMapper.fromObjectToDto(createdSubcategory);
    }
}
