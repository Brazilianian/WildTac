package com.wildtac.service;

import com.wildtac.domain.product.Product;
import com.wildtac.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
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

    /**
     * @throws EntityNotFoundException if entity was not found
     * @param id - field of product
     */
    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            log.warn(String.format("Deleting failed - product with id %s was not found", id));
            throw new EntityNotFoundException(String.format("Product with id %s was not found", id));
        }
        productRepo.deleteById(id);
        log.info(String.format("Product with id %s was deleted", id));
    }

    /**
     * @throws EntityNotFoundException if entity was not found
     * @param product - product with redacted data
     * @return Product - redacted product
     */
    public Product redactProduct(Product product) {
        if (!productRepo.existsById(product.getId())) {
            log.warn(String.format("Redacting failed - product %s was not found", product.getName()));
            throw new EntityNotFoundException(String.format("Product %s was not found", product.getName()));
        }

        Product redactedProduct = productRepo.save(product);
        log.info(String.format("Product %s was redacted", redactedProduct.getName()));
        return redactedProduct;
    }
}
