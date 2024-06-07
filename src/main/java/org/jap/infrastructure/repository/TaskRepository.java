package org.jap.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jap.infrastructure.entity.Task;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {
}
