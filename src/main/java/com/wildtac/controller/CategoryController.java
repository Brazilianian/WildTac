package com.wildtac.controller;

import com.wildtac.domain.product.category.Category;
import com.wildtac.dto.product.category.CategoryDto;
import com.wildtac.mapper.CategoryMapper;
import com.wildtac.repository.CategoryRepo;
import com.wildtac.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return categoryMapper.fromObjectListToDtoList(categories);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<?> getCategoryByName(@PathVariable(name = "categoryName") String categoryName) {
        Category category;
        try {
            category = categoryService.getCategoryByName(categoryName);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(categoryMapper.fromObjectToDto(category));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto category) {
        Category createdCategory;
        try {
            createdCategory = categoryService.createCategory(categoryMapper.fromDtoToObject(category));
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(categoryMapper.fromObjectToDto(createdCategory));
    }
}

