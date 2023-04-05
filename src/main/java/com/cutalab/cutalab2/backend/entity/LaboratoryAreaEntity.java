package com.cutalab.cutalab2.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "laboratory_area")
public class LaboratoryAreaEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "laboratoryAreaEntity")
    @Column(nullable = false)
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