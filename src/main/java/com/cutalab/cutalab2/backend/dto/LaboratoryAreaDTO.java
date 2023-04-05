package com.cutalab.cutalab2.backend.dto;

import com.cutalab.cutalab2.backend.entity.LaboratoryAreaEntity;
import com.cutalab.cutalab2.backend.entity.LaboratoryEntity;

import java.util.List;

public class LaboratoryAreaDTO {

    private Integer id;
    private String name;
    private List<LaboratoryEntity> laboratoryEntityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LaboratoryEntity> getLaboratoryEntityList() {
        return laboratoryEntityList;
    }

    public void setLaboratoryEntityList(List<LaboratoryEntity> laboratoryEntityList) {
        this.laboratoryEntityList = laboratoryEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaboratoryAreaEntity)) return false;

        LaboratoryAreaEntity that = (LaboratoryAreaEntity) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}