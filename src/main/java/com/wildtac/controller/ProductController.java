package com.wildtac.controller;

import com.wildtac.domain.product.Product;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.mapper.ProductMapper;
import com.wildtac.service.ProductService;
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

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    @ResponseBody
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getAllProducts();

        return productMapper.fromObjectListToDtoList(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product;
        try {
            product = productService.getProduct(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        ProductDto productDto = productMapper.fromObjectToDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Product createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.fromDtoToObject(productDto);

        return productService.createProduct(product);
    }
}
