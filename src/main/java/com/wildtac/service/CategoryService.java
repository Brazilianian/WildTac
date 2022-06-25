package com.wildtac.service;

import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.repository.CategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    /**
     *
     * @param category - category of the product
     * @return Category - created category
     * @throws EntityExistsException if category with the same name already exists
     */
    public Category createCategory(Category category) {
        if (categoryRepo.existsByName(category.getName())) {
            log.warn(String.format("Failed to create category - the category %s already exists", category.getName()));
            throw new EntityExistsException(String.format("Category %s already exists", category.getName()));
        }

        log.info(String.format("Category %s was saved", category.getName()));
        return categoryRepo.save(category);
    }


    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    /**
     *
     * @param categoryName - name of the category
     * @return category by the requested name
     * @throws EntityNotFoundException if category was not found
     */
    public Category getCategoryByName(String categoryName) {
        if (!categoryRepo.existsByName(categoryName)) {
            throw new EntityNotFoundException(String.format("Category %s was not found", categoryName));
        }

        return categoryRepo.findByName(categoryName);
    }

    /**
     *
     * @param category - category of subcategory
     * @param subcategory - new subcategory you would like to add
     * @return subcategory - added subcategory
     * @throws EntityExistsException - if subcategory with the same name already exists
     */
    public Subcategory addSubcategoryOfCategory(Category category, Subcategory subcategory) {
        Optional<Subcategory> subcategoryOptional = category.getSubcategories()
                .stream()
                .filter(s -> s.getName().equals(subcategory.getName()))
                .findFirst();

        if (subcategoryOptional.isPresent()) {
            log.warn(String.format("Failed to create subcategory - the subcategory %s of category %s already exists", subcategory.getName(), category.getName()));
            throw new EntityExistsException(String.format("Subcategory %s of category %s already exists", subcategory.getName(), category.getName()));
        }

        category.getSubcategories().add(subcategory);

        Category updatedCategory = categoryRepo.save(category);

        log.info(String.format("Subcategory %s of category %s was created", subcategory.getName(), category.getName()));

        return updatedCategory.getSubcategories()
                .stream()
                .filter(s -> s.getName().equals(subcategory.getName()))
                .findFirst().get();
    }
}
