package org.heb.api.images.services;

import org.heb.api.images.entities.ImageEntity;
import org.heb.api.images.exceptions.NotFoundException;
import org.heb.api.images.exceptions.RestRequestException;
import org.heb.api.images.mappers.ImageMapper;
import org.heb.api.images.models.Image;
import org.heb.api.images.repos.ImageRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    ImageRepo imageRepo;

    @Mock
    ImageMapper imageMapper;

    @Mock
    ObjectDetectionService objectDetectionService;

    @InjectMocks
    ImageService imageService;

    @Test
    void findById_returns_correctly() throws NotFoundException {
        var id = UUID.randomUUID();
        var imageEntity = new ImageEntity();
        var image = new Image();
        when(imageRepo.findById(id)).thenReturn(Optional.of(imageEntity));
        when(imageMapper.toModel(imageEntity)).thenReturn(image);
        assertThat(imageService.findById(id), equalTo(image));
    }

    @Test
    void findById_throws_correctly() {
        var id = UUID.randomUUID();
        when(imageRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> imageService.findById(id));
    }

    @Test
    void search_returns_without_tags() {
        var imageEntities = List.of(new ImageEntity());
        var images = List.of(new Image());
        when(imageRepo.findAll()).thenReturn(imageEntities);
        when(imageMapper.toModels(imageEntities)).thenReturn(images);
        assertThat(imageService.search(null), equalTo(images));
    }

    @Test
    void search_returns_with_tags() {
        var imageEntities = List.of(new ImageEntity());
        var images = List.of(new Image());
        when(imageRepo.findAllByTags(List.of("foo", "bar"), 2)).thenReturn(imageEntities);
        when(imageMapper.toModels(imageEntities)).thenReturn(images);
        assertThat(imageService.search(List.of("foo", "bar")), equalTo(images));
    }

    @Test
    void create_saves_with_complete_object() throws RestRequestException {
        var image = new Image();
        image.setLabel("foo");
        image.setIsObjectDetectionEnabled(true);
        image.setLocation("bar");
        var imageEntity = new ImageEntity();
        when(imageMapper.toEntity(image)).thenReturn(imageEntity);
        when(imageRepo.save(imageEntity)).thenReturn(imageEntity);
        var newImage = new Image();
        when(imageMapper.toModel(imageEntity)).thenReturn(newImage);
        when(objectDetectionService.getTags(image.getLocation())).thenReturn(List.of("tag"));
        assertThat(imageService.create(image), equalTo(newImage));
        verify(imageMapper, times(1)).toEntity(
                argThat(i ->
                        "foo".equals(i.getLabel()) &&
                                i.getIsObjectDetectionEnabled() &&
                                "bar".equals(i.getLocation()) &&
                                i.getTags().contains("tag") &&
                                i.getTags().size() == 1));
    }

    @Test
    void create_saves_with_minimal_object() throws RestRequestException {
        var image = new Image();
        image.setLocation("bar");
        var imageEntity = new ImageEntity();
        when(imageMapper.toEntity(image)).thenReturn(imageEntity);
        when(imageRepo.save(imageEntity)).thenReturn(imageEntity);
        var newImage = new Image();
        when(imageMapper.toModel(imageEntity)).thenReturn(newImage);
        assertThat(imageService.create(image), equalTo(newImage));
        verify(imageMapper, times(1)).toEntity(
                argThat(i ->
                        "bar".equals(i.getLabel()) &&
                                !i.getIsObjectDetectionEnabled() &&
                                "bar".equals(i.getLocation())));
    }


}
