package org.heb.api.images.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity(name = "images")
@Getter
@Setter
public class ImageEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;

    @NotNull
    String location;

    String label;

    Boolean isObjectDetectionEnabled;

    @ElementCollection
    @CollectionTable(name = "image_tags", joinColumns = @JoinColumn(name = "image_id"))
    @Column(name = "tag")
    Set<String> tags;
}
