package org.heb.api.images.services;

import org.hamcrest.Matchers;
import org.heb.api.images.exceptions.RestRequestException;
import org.heb.api.images.models.imagga.Tag;
import org.heb.api.images.models.imagga.TagResponse;
import org.heb.api.images.models.imagga.TagResult;
import org.heb.api.images.util.RestRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)

public class ObjectDetectionServiceTest {

    @Mock
    RestRepo imaggaRepo;

    @InjectMocks
    ObjectDetectionService objectDetectionService;

    @Test
    void getTags_returns_correctly() throws RestRequestException {
        var url = "foo";
        var tagResponse = new TagResponse();
        var tagResult = new TagResult();
        var tag = new Tag();
        tag.setConfidence(0.5);
        tag.setTag(Map.of("en", "dog"));
        tagResult.setTags(List.of(tag));
        tagResponse.setResult(tagResult);

        Mockito.when(imaggaRepo.execute(any(), any())).thenReturn(tagResponse);

        var res = objectDetectionService.getTags(url);
        assertThat(res.size(), Matchers.equalTo(1));
        assertThat(res, Matchers.contains("dog"));

        Mockito.verify(imaggaRepo, times(1)).execute(
                argThat(m ->
                        m.size() == 1 &&
                                url.equals(m.get("image_url").get(0))),
                eq(TagResponse.class));
    }
}
