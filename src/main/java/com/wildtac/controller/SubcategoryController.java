package com.wildtac.controller;

import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.dto.product.category.SubcategoryDto;
import com.wildtac.mapper.product.category.SubcategoryMapper;
import com.wildtac.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

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
    public ResponseEntity<?> createSubcategory(@PathVariable(name = "categoryId") Long categoryId,
                                               @RequestBody SubcategoryDto subcategoryDto) {
        Category category;
        try {
            category = categoryService.getCategoryById(categoryId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        Subcategory createdSubcategory;
        try {
            createdSubcategory = categoryService.addSubcategoryOfCategory(category, subcategoryMapper.fromDtoToObject(subcategoryDto));
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(subcategoryMapper.fromObjectToDto(createdSubcategory));
    }
}
