package com.wildtac.repository;

import com.wildtac.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findBySubcategory_Id(Long id, Pageable pageable);


}
