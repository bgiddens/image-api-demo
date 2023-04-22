package org.heb.api.images.services;

import lombok.AllArgsConstructor;
import org.heb.api.images.exceptions.RestRequestException;
import org.heb.api.images.models.imagga.TagResponse;
import org.heb.api.images.util.RestRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ObjectDetectionService {

    private final RestRepo imaggaRepo;

    public List<String> getTags(String url) throws RestRequestException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("image_url", url);
        var imaggaResponse = imaggaRepo.execute(params, TagResponse.class);
        return imaggaResponse.getResult().getTags().stream().map(x -> x.getTag().get("en")).collect(Collectors.toList());
    }
}
