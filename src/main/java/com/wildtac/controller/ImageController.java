package com.wildtac.controller;

import com.wildtac.domain.Image;
import com.wildtac.dto.ImageDto;
import com.wildtac.mapper.ImageMapper;
import com.wildtac.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(ImageService imageService, ImageMapper imageMapper) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ImageDto getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);

        return imageMapper.fromObjectToDto(image);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ImageDto createImage(@RequestBody ImageDto imageDto) {
        Image image = imageService.saveImage(imageMapper.fromDtoToObject(imageDto));

        return imageMapper.fromObjectToDto(image);
    }
}
