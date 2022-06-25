package com.wildtac.repository;

import com.wildtac.domain.product.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Category findByName(String name);


}
