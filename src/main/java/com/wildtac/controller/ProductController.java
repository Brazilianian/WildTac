package com.wildtac.controller;

import com.wildtac.domain.Image;
import com.wildtac.domain.product.Product;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.mapper.ImageMapper;
import com.wildtac.mapper.product.ProductMapper;
import com.wildtac.service.ImageService;
import com.wildtac.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ProductController(ProductService productService, ProductMapper productMapper, ImageService imageService, ImageMapper imageMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @GetMapping
    @ResponseBody
    public List<ProductDto> getProducts(@PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);

        return productMapper.fromObjectListToDtoList(products.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "id") Long id) {
        Product product;
        try {
            product = productService.getProductById(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        ProductDto productDto = productMapper.fromObjectToDto(product);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable(name = "subcategoryId") Long subcategory,
                                                   Pageable pageable) {
        Page<Product> productsPage;
        try {
            productsPage = productService.getProductBySubcategory(subcategory, pageable);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(productMapper.fromObjectListToDtoList(productsPage.getContent()));
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<?> getImagesOfProduct(@PathVariable(name = "id") Long productId) {
        List<Image> productImages = imageService.getImagesByParentId(productId);
        return new ResponseEntity<>(imageMapper.fromObjectListToDtoList(productImages), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product createdProduct = productService.createProduct(productMapper.fromDtoToObject(productDto));

        return ResponseEntity.ok(productMapper.fromObjectToDto(createdProduct));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping
    public ResponseEntity<?> redactProduct(@RequestBody ProductDto product) {
        Product redactedProduct;
        try {
            redactedProduct = productService.redactProduct(productMapper.fromDtoToObject(product));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(productMapper.fromObjectToDto(redactedProduct));
    }
}
