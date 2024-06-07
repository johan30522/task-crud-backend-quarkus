package org.jap.infrastructure.entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="Images")
public class Image {
    @Id
    @GeneratedValue
    private Long id;
    private Long idTask;
    private String name;
    private String imageUrl;
    private String imageId;

    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(idTask, image.idTask) && Objects.equals(name, image.name) && Objects.equals(imageUrl, image.imageUrl) && Objects.equals(imageId, image.imageId) && Objects.equals(task, image.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTask, name, imageUrl, imageId, task);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", idTask=" + idTask +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageId='" + imageId + '\'' +
                ", task=" + task +
                '}';
    }
}
