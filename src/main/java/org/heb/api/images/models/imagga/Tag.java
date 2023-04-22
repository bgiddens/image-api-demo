package org.heb.api.images.models.imagga;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Tag {
    double confidence;
    Map<String, String> tag;
}
