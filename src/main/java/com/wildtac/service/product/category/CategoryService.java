package com.wildtac.service.product.category;

import com.wildtac.domain.product.category.Category;
import com.wildtac.exception.alreadyexists.CategoryAlreadyExistsException;
import com.wildtac.exception.wasnotfound.CategoryWasNotFoundException;
import com.wildtac.repository.product.category.CategoryRepo;
import com.wildtac.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * The method creates new category if there is no category with the same name
     * The field name must be unique and can`t repeats
     * @param category - category of the product
     * @return Category - created category
     * @throws CategoryAlreadyExistsException if category with the same name already exists
     */
    public Category createCategory(Category category) {
        if (categoryRepo.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException(String.format("Failed to create new category - the category '%s' already exists", category.getName()));
        }

        Category categoryCreated = categoryRepo.save(category);

        categoryCreated.getImage().setParentId(categoryCreated.getId());
        imageService.saveImage(categoryCreated.getImage());

        log.info(String.format("Category '%s' was saved", category));
        return categoryCreated;
    }

    /**
     * The method finds all categories
     * @return List<Category> - list of all categories
     */
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    /**
     * The method finds category by field 'id'
     * @param categoryId - id of the category
     * @return category by the requested id
     * @throws CategoryAlreadyExistsException if category was not found
     */
    public Category getCategoryById(Long categoryId) {

        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryWasNotFoundException(String.format("Category with id '%s' was not found", categoryId)));
    }



    /**
     * The method redacts category
     * Replaces all old information
     * @param category - new category with id of old category
     * @return category - updated category
     * @throws CategoryWasNotFoundException - if category was not found by id
     */
    public Category redactCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepo.findById(category.getId());
        if (categoryOptional.isEmpty()) {
            throw new CategoryWasNotFoundException(String.format("Failed to redact - the category with id '%s' was not found", category.getId()));
        }

        Category updatedCategory = categoryRepo.save(category);
        log.info(String.format("Category '%s' was updated", category));
        return updatedCategory;
    }
}
