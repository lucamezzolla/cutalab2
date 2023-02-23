package com.cutalab.cutalab2.backend.entity.dashboards.disks;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "disks_style")
public class DiskStyleEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 45)
    private String name;

    public DiskStyleEntity() {
    }

    public DiskStyleEntity(String name) {
        this.name = name;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "diskStyleList",
            joinColumns = @JoinColumn(name = "style_id"),
            inverseJoinColumns = @JoinColumn(name = "disk_id")
    )
    private List<DiskEntity> disksList = new ArrayList<>();

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

    public List<DiskEntity> getDisksList() {
        return disksList;
    }

    public void setDisksList(List<DiskEntity> disksList) {
        this.disksList = disksList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiskStyleEntity)) return false;
        DiskStyleEntity that = (DiskStyleEntity) o;
        return getId().equals(that.getId()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return name;
    }
}