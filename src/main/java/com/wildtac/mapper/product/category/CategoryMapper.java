package com.wildtac.mapper.product.category;

import com.wildtac.domain.product.category.Category;
import com.wildtac.dto.product.category.CategoryDto;
import com.wildtac.mapper.StructMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMapper implements StructMapper<CategoryDto, Category> {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Category> fromDtoListToObjectList(List<CategoryDto> categoriesDto) {
        return modelMapper.map(categoriesDto, new TypeToken<List<Category>>() {}.getType());
    }

    @Override
    public List<CategoryDto> fromObjectListToDtoList(List<Category> categories) {
        return modelMapper.map(categories, new TypeToken<List<CategoryDto>>() {}.getType());
    }

    @Override
    public Category fromDtoToObject(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    @Override
    public CategoryDto fromObjectToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
