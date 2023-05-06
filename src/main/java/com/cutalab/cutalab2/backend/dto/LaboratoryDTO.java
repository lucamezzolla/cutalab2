package com.cutalab.cutalab2.backend.dto;

import java.time.LocalDateTime;

public class LaboratoryDTO {


    private Integer id;
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LaboratoryAreaDTO laboratoryAreaDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LaboratoryAreaDTO getLaboratoryAreaDTO() {
        return laboratoryAreaDTO;
    }

    public void setLaboratoryAreaDTO(LaboratoryAreaDTO laboratoryAreaDTO) {
        this.laboratoryAreaDTO = laboratoryAreaDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaboratoryDTO)) return false;

        LaboratoryDTO that = (LaboratoryDTO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}