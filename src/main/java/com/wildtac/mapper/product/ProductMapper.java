package com.wildtac.mapper.product;

import com.wildtac.domain.product.Product;
import com.wildtac.dto.product.ProductDto;
import com.wildtac.mapper.StructMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMapper implements StructMapper<ProductDto, Product> {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Product> fromDtoListToObjectList(List<ProductDto> productsDto) {
        return modelMapper.map(productsDto, new TypeToken<List<Product>>() {}.getType());
    }

    @Override
    public List<ProductDto> fromObjectListToDtoList(List<Product> products) {
        return modelMapper.map(products, new TypeToken<List<ProductDto>>() {}.getType());
    }

    @Override
    public Product fromDtoToObject(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

    @Override
    public ProductDto fromObjectToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }
}
