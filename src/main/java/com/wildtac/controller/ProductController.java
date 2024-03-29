package com.wildtac.controller;

import com.wildtac.domain.Image;
import com.wildtac.domain.product.Product;
import com.wildtac.dto.image.ImageDto;
import com.wildtac.dto.product.ProductCreateRequestDto;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.dto.product.ProductRedactRequestDto;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.ImageMapper;
import com.wildtac.mapper.product.ProductMapper;
import com.wildtac.service.ImageService;
import com.wildtac.service.product.ProductService;
import com.wildtac.service.product.category.CategoryService;
import com.wildtac.service.product.category.SubcategoryService;
import com.wildtac.utils.ValidationUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * This controller contains main endpoints of product system
 * Searching, creating, deleting etc
 */
@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getProducts(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);

        return productMapper.fromObjectListToDtoList(products.getContent());
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable(name = "id") Long id) {
        Product product = productService.getProductById(id);
        product = productService.countOfVisitIncrement(product);

        return productMapper.fromObjectToDto(product);
    }

    @GetMapping("/subcategory/{subcategoryId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDto> getProductsByCategory(@PathVariable(name = "subcategoryId") Long subcategory,
                                                  Pageable pageable) {
        Page<Product> productsPage = productService.getProductBySubcategory(subcategory, pageable);

        return new PageImpl<>(productMapper.fromObjectListToDtoList(productsPage.getContent()));
    }

    @GetMapping("/{id}/images")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ImageDto> getImagesOfProduct(@PathVariable(name = "id") Long productId) {
        List<Image> productImages = imageService.getImagesByParentId(productId);

        return imageMapper.fromObjectListToDtoList(productImages);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('product:write')")
    @ResponseBody
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductCreateRequestDto productDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException("Failed to create new product", errors);
        }
        Product createdProduct = productService.createProduct(productMapper.fromDtoToObject(productDto));

        return ResponseEntity.ok(productMapper.fromObjectToDto(createdProduct));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('product:delete')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('product:redact')")
    public ProductDto redactProduct(@RequestBody @Valid ProductRedactRequestDto productDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException(String.format("Failed to redact product with id '%s'", productDto.getId()), errors);
        }

        Product product = productMapper.fromDtoToObject(productDto);

        product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()));
        product.setSubcategory(subcategoryService.getSubcategoryById(productDto.getSubcategoryId()));

        Product redactedProduct = productService.redactProduct(product);
        return productMapper.fromObjectToDto(redactedProduct);
    }
}
