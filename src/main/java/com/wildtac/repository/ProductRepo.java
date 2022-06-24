package com.wildtac.repository;

import com.wildtac.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
