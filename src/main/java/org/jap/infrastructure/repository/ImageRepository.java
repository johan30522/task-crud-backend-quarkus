package org.jap.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jap.infrastructure.entity.Image;
@ApplicationScoped
public class ImageRepository implements PanacheRepository<Image> {
}
