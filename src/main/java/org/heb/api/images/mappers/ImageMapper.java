package org.heb.api.images.mappers;

import org.heb.api.images.entities.ImageEntity;
import org.heb.api.images.models.Image;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageEntity toEntity(Image image);

    Image toModel(ImageEntity imageEntity);

    List<Image> toModels(List<ImageEntity> imageEntities);
}
