package com.wildtac.service;

import com.sun.jdi.InternalException;
import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.repository.CategoryRepo;
import com.wildtac.repository.SubcategoryRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SubcategoryService {
    private final SubcategoryRepo subcategoryRepo;
    private final CategoryRepo categoryRepo;
    private final ImageService imageService;

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

        Category categoryUpdated = categoryRepo.save(category);

        Subcategory subcategoryCreated = categoryUpdated.getSubcategories()
                .stream()
                .filter(s -> s.getName().equals(subcategory.getName()))
                .findFirst()
                .orElseThrow(() -> new InternalException("Subcategory was not found"));

        subcategoryCreated.getImage().setParentId(subcategoryCreated.getId());
        imageService.saveImage(subcategoryCreated.getImage());

        log.info(String.format("The subcategory %s of category %s was created", subcategory.getName(), category.getName()));
        return subcategoryCreated;
    }

    public Subcategory getSubcategoryById(Long subcategoryId) {
        return subcategoryRepo
                .findById(subcategoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subcategory with id %s was not found", subcategoryId)));
    }
}
