package com.wildtac.controller;

import com.wildtac.domain.Image;
import com.wildtac.dto.image.ImageDto;
import com.wildtac.mapper.ImageMapper;
import com.wildtac.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(ImageService imageService, ImageMapper imageMapper) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ImageDto getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);

        return imageMapper.fromObjectToDto(image);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ImageDto createImage(@RequestBody ImageDto imageDto) {
        Image image = imageService.saveImage(imageMapper.fromDtoToObject(imageDto));

        return imageMapper.fromObjectToDto(image);
    }

    @GetMapping("/image-parent/{parentId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ImageDto> getImagesByParentId(@PathVariable(name = "parentId") Long parentId) {
        List<Image> images = imageService.getImagesByParentId(parentId);
        return imageMapper.fromObjectListToDtoList(images);
    }
}
