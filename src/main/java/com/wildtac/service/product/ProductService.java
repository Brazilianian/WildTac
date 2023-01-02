package com.wildtac.service.product;

import com.wildtac.domain.Image;
import com.wildtac.domain.product.Product;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.exception.wasnotfound.ProductWasNotFoundException;
import com.wildtac.exception.wasnotfound.SubcategoryWasNotFoundException;
import com.wildtac.repository.product.ProductRepo;
import com.wildtac.service.ImageService;
import com.wildtac.service.product.category.SubcategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.wildtac.exception.wasnotfound.ImageWasNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;
    private final SubcategoryService subcategoryService;
    private final ImageService imageService;

    public ProductService(ProductRepo productRepo, SubcategoryService subcategoryService, ImageService imageService) {
        this.productRepo = productRepo;
        this.subcategoryService = subcategoryService;
        this.imageService = imageService;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    /**
     * The method saves new product
     * Current product must contain information about it`s subcategory
     *
     * @param product - new product
     * @return createdProduct - product that was created
     * @throws SubcategoryWasNotFoundException - if subcategory was not found by id
     */
    public Product createProduct(Product product) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(product.getSubcategory().getId());

        product.setSubcategory(subcategory);
        product.setCategory(subcategory.getCategory());

        Product createdProduct = save(product);

        for (Image image : product.getImages()) {
            image.setParentId(createdProduct.getId());
        }

        createdProduct = save(product);

        log.info(String.format("Product '%s' was saved", createdProduct));
        return createdProduct;
    }

    /**
     * The method finds product by field 'id'
     *
     * @param id - field of product
     * @return Product
     * @throws ProductWasNotFoundException if product was not found
     */
    public Product getProductById(Long id) {
        return productRepo
                .findById(id)
                .orElseThrow(() -> new ProductWasNotFoundException(String.format("Product with id %s was not found", id)));
    }

    /**
     * The method changes product status to 'DELETED'
     * <p>
     * // TODO: 17.12.2022 it needs to complete the system with deleting entities via changing of it`s status
     *
     * @param id - field of product
     * @throws ProductWasNotFoundException if product was not found
     */
    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new ProductWasNotFoundException(String.format("Failed to delete product - the product with id '%s' was not found", id));
        }
        productRepo.deleteById(id);
        log.info(String.format("Product with id '%s' was deleted", id));
    }

    /**
     * The method redacts product and saves new changes
     * The method changes all field includes images but not category and subcategory.
     *
     * @param product - product with redacted data
     * @return Product - redacted product
     * @throws ProductWasNotFoundException if product was not found
     */
    public Product redactProduct(Product product) {

        Product productDb = productRepo.findById(product.getId())
                .orElseThrow(() -> new ProductWasNotFoundException(String.format("Failed to redact product - the product '%s' was not found", product)));

        removeImagesFromProduct(productDb);

        for (Image image : product.getImages()) {
            image.setParentId(product.getId());
        }

        Product redactedProduct = save(product);
        log.info(String.format("Product '%s' was redacted", redactedProduct));
        return redactedProduct;
    }

    /**
     * The private method that clear list of images of product and deleting it from DB
     *
     * @param product - selected product which images will be cleared and deleted
     * @throws ImageWasNotFoundException - if image was not found by id
     */
    private void removeImagesFromProduct(Product product) {
        List<Image> images = new ArrayList<>(product.getImages());
        product.getImages().clear();
        save(product);
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
     * @throws SubcategoryWasNotFoundException - if the subcategory does not exist
     */
    public Page<Product> getProductBySubcategory(Long subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(subcategoryId);

        return productRepo.findBySubcategory_Id(subcategory.getId(), pageable);
    }

    /**
     * The method increments count of visits of product
     *
     * @param product - product
     * @return Product - changed product
     */
    public Product countOfVisitIncrement(Product product) {
        product.visitCountIncrement();
        return save(product);
    }

    /**
     * The method saves product
     * @param product - product to save
     * @return - saved product
     */
    public Product save(Product product) {
        return productRepo.save(product);
    }
}
