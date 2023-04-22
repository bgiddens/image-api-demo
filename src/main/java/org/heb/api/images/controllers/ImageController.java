package org.heb.api.images.controllers;

import lombok.AllArgsConstructor;
import org.heb.api.images.exceptions.NotFoundException;
import org.heb.api.images.exceptions.RestRequestException;
import org.heb.api.images.models.Image;
import org.heb.api.images.services.ImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public List<Image> getImages(@RequestParam(required = false) List<String> tags) {
        return imageService.search(tags);
    }

    @GetMapping("/{imageId}")
    public Image getImage(@PathVariable String imageId) throws NotFoundException {
        var uuid = UUID.fromString(imageId);
        return imageService.findById(uuid);
    }

    @PostMapping
    public Image createImage(@RequestBody Image image) throws RestRequestException {
        return imageService.create(image);
    }
}
