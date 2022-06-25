package com.wildtac.mapper;

import com.wildtac.domain.product.category.Category;
import com.wildtac.domain.product.category.Subcategory;
import com.wildtac.dto.product.category.SubcategoryDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryMapper implements StructMapper<SubcategoryDto, Subcategory> {

    private final ModelMapper modelMapper;

    public SubcategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Subcategory> fromDtoListToObjectList(List<SubcategoryDto> subcategoriesDto) {
        return modelMapper.map(subcategoriesDto, new TypeToken<List<Category>>() {}.getType());
    }

    @Override
    public List<SubcategoryDto> fromObjectListToDtoList(List<Subcategory> subcategories) {
        return modelMapper.map(subcategories, new TypeToken<List<SubcategoryDto>>() {}.getType());
    }

    @Override
    public Subcategory fromDtoToObject(SubcategoryDto subcategoryDto) {
        return modelMapper.map(subcategoryDto, Subcategory.class);
    }

    @Override
    public SubcategoryDto fromObjectToDto(Subcategory subcategory) {
        return modelMapper.map(subcategory, SubcategoryDto.class);
    }
}
