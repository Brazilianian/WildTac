package com.wildtac.mapper;

import com.wildtac.domain.Image;
import com.wildtac.dto.image.ImageDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageMapper implements StructMapper<ImageDto, Image> {

    private final ModelMapper modelMapper;

    public ImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Image> fromDtoListToObjectList(List<ImageDto> imageDtoList) {
        return modelMapper.map(imageDtoList, new TypeToken<List<Image>>() {}.getType());
    }

    @Override
    public List<ImageDto> fromObjectListToDtoList(List<Image> imageList) {
        return modelMapper.map(imageList, new TypeToken<List<ImageDto>>() {}.getType());
    }

    @Override
    public Image fromDtoToObject(ImageDto imageDto) {
        return modelMapper.map(imageDto, Image.class);
    }

    @Override
    public ImageDto fromObjectToDto(Image image) {
        return modelMapper.map(image, ImageDto.class);
    }
}
