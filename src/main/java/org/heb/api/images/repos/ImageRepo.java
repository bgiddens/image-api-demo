package org.heb.api.images.repos;

import org.heb.api.images.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ImageRepo extends JpaRepository<ImageEntity, UUID> {

    @Query(value = "select i.* from images i inner join image_tags it on it.image_id = i.id " +
            "where it.tag in :tags group by i.id having count(i.id) = :tagCount", nativeQuery = true)
    List<ImageEntity> findAllByTags(List<String> tags, Integer tagCount);
}
