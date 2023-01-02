package com.wildtac.controller;

import com.wildtac.domain.Image;
import com.wildtac.dto.image.ImageDto;
import com.wildtac.exception.ValidationException;
import com.wildtac.mapper.ImageMapper;
import com.wildtac.service.ImageService;
import com.wildtac.utils.ValidationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/image-parent/{parentId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ImageDto> getImagesByParentId(@PathVariable(name = "parentId") Long parentId) {
        List<Image> images = imageService.getImagesByParentId(parentId);
        return imageMapper.fromObjectListToDtoList(images);
    }

    @GetMapping("/image-parent/{parentId}/index/{index}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ImageDto getImageByParentIdAndIndex(@PathVariable(name = "index") Long index,
                                            @PathVariable(name = "parentId") Long parentId) {
        Image image = imageService.getImageByParentIdAndIndex(parentId, index);
        return imageMapper.fromObjectToDto(image);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('image:write')")
    @ResponseBody
    public ImageDto createImage(@RequestBody @Valid ImageDto imageDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ValidationUtils.getErrors(bindingResult);
            throw new ValidationException("Failed to create new image", errors);
        }
        Image image = imageService.saveImage(imageMapper.fromDtoToObject(imageDto));

        return imageMapper.fromObjectToDto(image);
    }
}
