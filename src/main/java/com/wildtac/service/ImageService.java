package com.wildtac.service;

import com.wildtac.domain.Image;
import com.wildtac.exception.wasnotfound.ImageWasNotFoundException;
import com.wildtac.repository.ImageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @throws ImageWasNotFoundException - if image was not found by id
     */
    public Image getImageById(Long id) {

        return imageRepo.findById(id)
                .orElseThrow(() -> new ImageWasNotFoundException(String.format("Image with id '%s' was not found", id)));
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
        log.info(String.format("New image '%s' was saved", imageCreated));

        return imageCreated;
    }

    /**
     * The method deletes image by field 'id'
     * @param id - field if image
     * @throws ImageWasNotFoundException - if image was not found by id
     */
    public void deleteImageById(Long id) {
        if (!imageRepo.existsById(id)) {
            throw new ImageWasNotFoundException(String.format("Failed to delete image - image with id '%s' was not found", id));
        }
        imageRepo.deleteById(id);
    }

    /**
     * The method find image by it`s parent id and it`s index
     * @param parentId - id of parent
     * @param index - the index of image
     * @return Image - founded image
     * @throws ImageWasNotFoundException - if image was not found by parentId and index
     */
    public Image getImageByParentIdAndIndex(Long parentId, Long index) {

        return getImagesByParentId(parentId)
                .stream()
                .filter(image -> image.getIndex() == index)
                .findFirst()
                .orElseThrow(() -> new ImageWasNotFoundException(String.format("The image with parentId '%s' and index '%s' was not found", parentId, index)));
    }
}
