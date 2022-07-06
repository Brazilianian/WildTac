package com.wildtac.service;

import com.wildtac.domain.Image;
import com.wildtac.repository.ImageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImageService {

    private final ImageRepo imageRepo;

    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    /**
     * The method finds image by field 'id'
     * @param id - field of image
     * @return - found image
     * @throws EntityNotFoundException - if image was not found by id
     */
    public Image getImageById(Long id) {
        Optional<Image> imageOptional = imageRepo.findById(id);
        if (imageOptional.isEmpty()) {
            throw new EntityNotFoundException(String.format("Image with id '%s' was not found", id));
        }

        return imageOptional.get();
    }

    /**
     * The method finds images by field 'parentId'
     * @param parentId - field of image
     * @return - list of images were found
     */
    public List<Image> getImagesByParentId(Long parentId) {
        return imageRepo.findByParentId(parentId);
    }

    /**
     * The method saves new image to DB
     * @param image - new image to save
     * @return - created image
     */
    public Image saveImage(Image image) {
        Image imageCreated = imageRepo.save(image);
        log.info("New image was saved");

        return imageCreated;
    }
}
