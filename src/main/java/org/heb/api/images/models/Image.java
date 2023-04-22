package org.heb.api.images.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Image {

    UUID id;

    String location;

    String label;

    Boolean isObjectDetectionEnabled;

    List<String> tags;
}
