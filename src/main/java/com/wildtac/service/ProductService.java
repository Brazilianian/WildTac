package com.wildtac.service;

import com.wildtac.domain.product.Product;
import com.wildtac.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


    public Product createProduct(Product product) {
        System.out.println(product);
        return productRepo.save(product);
    }
}
