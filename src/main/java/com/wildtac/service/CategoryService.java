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
    private final ImageService imageService;

    public CategoryService(CategoryRepo categoryRepo, ImageService imageService) {
        this.categoryRepo = categoryRepo;
        this.imageService = imageService;
    }

    /**
     * @param category - category of the product
     * @return Category - created category
     * @throws EntityExistsException if category with the same name already exists
     */
    public Category createCategory(Category category) {
        if (categoryRepo.existsByName(category.getName())) {
            throw new EntityExistsException(String.format("Failed to create new category - the category %s already exists", category.getName()));
        }

        Category categoryCreated = categoryRepo.save(category);
        log.info(String.format("Category %s was saved", category.getName()));
        return categoryCreated;
    }


    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    /**
     * @param categoryId - id of the category
     * @return category by the requested id
     * @throws EntityNotFoundException if category was not found
     */
    public Category getCategoryById(Long categoryId) {

        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category %s was not found", categoryId)));
    }

    /**
     * @param category    - category of subcategory
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
            throw new EntityExistsException(String.format("Failed to add subcategory to category - the subcategory %s of category %s already exists", subcategory.getName(), category.getName()));
        }

        category.getSubcategories().add(subcategory);
        subcategory.setCategory(category);

        Category updatedCategory = categoryRepo.save(category);

        updatedCategory.getImage().setParentId(updatedCategory.getId());
        imageService.saveImage(updatedCategory.getImage());

        updatedCategory = categoryRepo.save(category);

        log.info(String.format("The subcategory %s of category %s was created", subcategory.getName(), category.getName()));

        return updatedCategory.getSubcategories()
                .stream()
                .filter(s -> s.getName().equals(subcategory.getName()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Failed to add subcategory to category - subcategory %s was not found in category %s", subcategory.getName(), category.getName())));
    }

    /**
     * @param category - new category with id of old category
     * @return category - updated category
     * @throws EntityNotFoundException - if category was not found by id
     */
    public Category redactCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepo.findById(category.getId());
        if (categoryOptional.isEmpty()) {
            throw new EntityNotFoundException(String.format("Failed to redact - the category with id %s was not found", category.getId()));
        }

        Category updatedCategory = categoryRepo.save(category);
        log.info(String.format("Category %s was updated", category.getName()));
        return updatedCategory;
    }
}
