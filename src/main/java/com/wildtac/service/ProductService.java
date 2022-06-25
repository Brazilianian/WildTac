package com.wildtac.service;

import com.wildtac.domain.product.Product;
import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.repository.CategoryRepo;
import com.wildtac.repository.ProductRepo;
import com.wildtac.repository.SubcategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final SubcategoryRepo subcategoryRepo;

    public ProductService(ProductRepo productRepo, SubcategoryRepo subcategoryRepo) {
        this.productRepo = productRepo;
        this.subcategoryRepo = subcategoryRepo;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }


    /**
     *
     * @param product - new product
     * @return createdProduct - product that was created
     * @throws EntityNotFoundException - if subcategory was not found by id
     */
    public Product createProduct(Product product) {
        Optional<Subcategory> subcategoryOptional = subcategoryRepo.findById(product.getSubcategory().getId());
        if (subcategoryOptional.isEmpty()) {
            log.warn(String.format("Failed to create product %s - subcategory with id %s does not exist", product.getName(), product.getSubcategory().getId()));
            throw new EntityNotFoundException(String.format("Subcategory with id %s does not exist", product.getSubcategory().getId()));
        }

        product.setSubcategory(subcategoryOptional.get());
        Product createdProduct = productRepo.save(product);
        log.info(String.format("Product '%s' was saved", createdProduct.getName()));
        return createdProduct;
    }

    /**
     * @param id - field of product
     * @return Product
     * @throws EntityNotFoundException if product was not found
     */
    public Product getProduct(Long id) {
       return productRepo
               .findById(id)
               .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s was not found", id)));
    }

    /**
     * @param id - field of product
     * @throws EntityNotFoundException if product was not found
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
     * @param product - product with redacted data
     * @return Product - redacted product
     * @throws EntityNotFoundException if product was not found
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

    /**
     *
     * @param subcategoryId - id of subcategory of the products
     * @param pageable - info about page of products
     * @return Page<Product> - page of products
     * @throws EntityNotFoundException if the subcategory does not exist
     */
    public Page<Product> getProductBySubcategory(Long subcategoryId, Pageable pageable) {
        Optional<Subcategory> subcategoryOptional = subcategoryRepo.findById(subcategoryId);

        if (subcategoryOptional.isEmpty()) {
            throw new EntityNotFoundException(String.format("Subcategory %s was not found", subcategoryId));
        }

        return productRepo.findBySubcategory_Id(subcategoryOptional.get().getId(), pageable);
    }
}
