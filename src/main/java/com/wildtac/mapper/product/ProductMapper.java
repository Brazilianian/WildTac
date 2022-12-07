package com.wildtac.mapper.product;

import com.wildtac.domain.product.Product;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.mapper.StructMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductMapper implements StructMapper<ProductDto, Product> {

    private final ModelMapper modelMapper;

    @Override
    public List<Product> fromDtoListToObjectList(List<ProductDto> productsDto) {
        return modelMapper.map(productsDto, new TypeToken<List<Product>>() {}.getType());
    }

    @Override
    public List<ProductDto> fromObjectListToDtoList(List<Product> products) {
        List<ProductDto> productDtoList = modelMapper.map(products, new TypeToken<List<ProductDto>>() {}.getType());
        productDtoList.forEach(productDto
                -> productDto.setImageCount(
                        products
                                .stream()
                                .filter(product -> productDto.getId().equals(product.getId()))
                                .findFirst().get().getImageCount()));
        return productDtoList;
    }

    @Override
    public Product fromDtoToObject(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    @Override
    public ProductDto fromObjectToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setImageCount(product.getImageCount());
        return productDto;
    }
}
