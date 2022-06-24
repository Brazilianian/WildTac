package com.wildtac.controller;

import com.wildtac.domain.product.Product;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.mapper.ProductMapper;
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
import java.util.stream.Collectors;

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
    public List<ProductDto> getProducts(@PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);

        return productMapper.fromObjectListToDtoList(products.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "id") Long id) {
        Product product;
        try {
            product = productService.getProduct(id);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        ProductDto productDto = productMapper.fromObjectToDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.fromDtoToObject(productDto);

        return ResponseEntity.ok(productService.createProduct(product));
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
