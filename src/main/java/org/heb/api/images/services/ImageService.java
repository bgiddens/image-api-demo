package org.heb.api.images.services;

import lombok.AllArgsConstructor;
import org.heb.api.images.exceptions.NotFoundException;
import org.heb.api.images.exceptions.RestRequestException;
import org.heb.api.images.mappers.ImageMapper;
import org.heb.api.images.models.Image;
import org.heb.api.images.repos.ImageRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepo imageRepo;
    private final ImageMapper imageMapper;
    private final ObjectDetectionService objectDetectionService;

    public Image findById(UUID uuid) throws NotFoundException {
        return imageMapper.toModel(imageRepo.findById(uuid).orElseThrow(NotFoundException::new));
    }


    public List<Image> search(List<String> tags) {
        var images =
                (tags == null) ?
                        imageRepo.findAll() :
                        imageRepo.findAllByTags(tags, tags.size());
        return imageMapper.toModels(images);
    }

    public Image create(Image image) throws RestRequestException {
        if (image.getLabel() == null) {
            image.setLabel(image.getLocation());
        }
        if (image.getIsObjectDetectionEnabled() == null) {
            image.setIsObjectDetectionEnabled(false);
        }
        if (image.getIsObjectDetectionEnabled()) {
            image.setTags(objectDetectionService.getTags(image.getLocation()));
        }
        var imageEntity = imageRepo.save(imageMapper.toEntity(image));
        return imageMapper.toModel(imageEntity);
    }
}
