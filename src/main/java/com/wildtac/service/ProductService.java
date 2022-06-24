package com.wildtac.service;

import com.wildtac.domain.product.Product;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


    public Product createProduct(Product product) {
        Product createdProduct = productRepo.save(product);
        log.info(String.format("Product '%s' was saved", createdProduct.getName()));
        return createdProduct;
    }

    /**
     * @throws EntityNotFoundException if entity was not found
     * @param id - field of product
     * @return Product
     */
    public Product getProduct(Long id) {
       return productRepo
               .findById(id)
               .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s was not found", id)));
    }
}
