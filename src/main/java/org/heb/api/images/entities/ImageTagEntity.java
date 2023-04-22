package org.heb.api.images.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@Setter
public class ImageTagEntity {

    @NotNull
    String tag;
}
