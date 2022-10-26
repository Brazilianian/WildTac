package com.wildtac.service;

import com.wildtac.domain.Image;
import com.wildtac.domain.product.Product;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.repository.ProductRepo;
import com.wildtac.repository.SubcategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final SubcategoryRepo subcategoryRepo;
    private final ImageService imageService;

    public ProductService(ProductRepo productRepo, SubcategoryRepo subcategoryRepo, ImageService imageService) {
        this.productRepo = productRepo;
        this.subcategoryRepo = subcategoryRepo;
        this.imageService = imageService;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    /**
     * The method saves new product to DB
     *
     * @param product - new product
     * @return createdProduct - product that was created
     * @throws EntityNotFoundException - if subcategory was not found by id
     */
    public Product createProduct(Product product) {
        Subcategory subcategory = subcategoryRepo.findById(product.getSubcategory().getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Failed to create new product - the subcategory with id %s does not exist", product.getSubcategory().getId())));

        product.setSubcategory(subcategory);
        product.setCategory(subcategory.getCategory());

        Product createdProduct = productRepo.save(product);

        for (Image image : product.getImages()) {
            image.setParentId(createdProduct.getId());
            imageService.saveImage(image);
        }

        log.info(String.format("Product %s was saved", createdProduct.getName()));
        return createdProduct;
    }

    /**
     * The method finds product by field 'id'
     *
     * @param id - field of product
     * @return Product
     * @throws EntityNotFoundException if product was not found
     */
    public Product getProductById(Long id) {
        return productRepo
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s was not found", id)));
    }

    /**
     * The method changes product status to 'DELETED'
     *
     * @param id - field of product
     * @throws EntityNotFoundException if product was not found
     */
    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new EntityNotFoundException(String.format("Failed to delete product - the product with id %s was not found", id));
        }
        productRepo.deleteById(id);
        log.info(String.format("Product with id %s was deleted", id));
    }

    /**
     * The method redacts product and saves new changes to DB
     *
     * @param product - product with redacted data
     * @return Product - redacted product
     * @throws EntityNotFoundException if product was not found
     */
    public Product redactProduct(Product product) {

        Product productDb = productRepo.findById(product.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Failed to redact product - the product %s was not found", product.getName())));

        removeImagesFromProduct(productDb);

        for (Image image : product.getImages()) {
            image.setParentId(product.getId());
        }

        Product redactedProduct = productRepo.save(product);
        log.info(String.format("Product %s was redacted", redactedProduct.getName()));
        return redactedProduct;
    }

    private void removeImagesFromProduct(Product productDb) {
        List<Image> images = new ArrayList<>(productDb.getImages());
        productDb.getImages().clear();
        productRepo.save(productDb);
        for (Image image : images) {
            imageService.deleteImageById(image.getId());
        }
    }

    /**
     * The method is returned page of product by the pageable info
     *
     * @param subcategoryId - id of subcategory of the products
     * @param pageable      - info about page of products
     * @return Page<Product> - page of products
     * @throws EntityNotFoundException if the subcategory does not exist
     */
    public Page<Product> getProductBySubcategory(Long subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryRepo.findById(subcategoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Subcategory %s was not found", subcategoryId)));

        return productRepo.findBySubcategory_Id(subcategory.getId(), pageable);
    }
}
