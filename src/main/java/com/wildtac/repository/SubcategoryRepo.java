package com.wildtac.repository;

import com.wildtac.domain.product.category.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SubcategoryRepo extends CrudRepository<Subcategory, Long> {
    Page<Subcategory> findAll (Pageable pageable);

    boolean existsByName(String name);

}
