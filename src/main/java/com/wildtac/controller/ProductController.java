package com.wildtac.controller;

import com.wildtac.domain.product.Product;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.mapper.ProductMapper;
import com.wildtac.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Product createProduct(@RequestBody ProductDto productDto) {
        System.out.println(productDto);
        Product product = productMapper.fromDtoToObject(productDto);

        return productService.createProduct(product);
    }
}
