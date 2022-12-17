package com.wildtac.service.product.category;

import com.sun.jdi.InternalException;
import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.exception.alreadyexists.SubcategoryAlreadyExistsException;
import com.wildtac.exception.wasnotfound.SubcategoryWasNotFoundException;
import com.wildtac.repository.product.category.CategoryRepo;
import com.wildtac.repository.product.category.SubcategoryRepo;
import com.wildtac.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SubcategoryService {
    private final SubcategoryRepo subcategoryRepo;
    private final CategoryRepo categoryRepo;
    private final ImageService imageService;

    /**
     * The method add subcategory to category.
     * The name of subcategories of category must be unique and can`t be repeated
     * @param category - category of subcategory
     * @param subcategory - new subcategory you would like to add
     * @return subcategory - added subcategory
     * @throws SubcategoryAlreadyExistsException - if subcategory with the same name already exists in this category
     * @throws InternalException - if already saved subcategory was not found in its category. The way of solution - check how entity saves
     */
    public Subcategory addSubcategoryOfCategory(Category category, Subcategory subcategory) {
        Optional<Subcategory> subcategoryOptional = category.getSubcategories()
                .stream()
                .filter(s -> s.getName().equals(subcategory.getName()))
                .findFirst();

        if (subcategoryOptional.isPresent()) {
            throw new SubcategoryAlreadyExistsException(String.format("Failed to add subcategory to category - the subcategory %s of category %s already exists", subcategory.getName(), category.getName()));
        }

        category.getSubcategories().add(subcategory);
        subcategory.setCategory(category);

        Category categoryUpdated = categoryRepo.save(category);

        Subcategory subcategoryCreated = categoryUpdated.getSubcategories()
                .stream()
                .filter(s -> s.getName().equals(subcategory.getName()))
                .findFirst()
                .orElseThrow(() -> new InternalException("Subcategory was not found"));

        subcategoryCreated.getImage().setParentId(subcategoryCreated.getId());
        imageService.saveImage(subcategoryCreated.getImage());

        log.info(String.format("The subcategory '%s' of category '%s' was created", subcategory, category));
        return subcategoryCreated;
    }

    /**
     * The method finds subcategory by it`s field 'id'
     * @param subcategoryId - field of subcategory
     * @return Subcategory
     * @throws SubcategoryWasNotFoundException - if subcategory was not found
     */
    public Subcategory getSubcategoryById(Long subcategoryId) {
        return subcategoryRepo
                .findById(subcategoryId)
                .orElseThrow(() -> new SubcategoryWasNotFoundException(String.format("Subcategory with id '%s' was not found", subcategoryId)));
    }
}
