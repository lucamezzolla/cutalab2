package com.cutalab.cutalab2.backend.dto.dashboards.disks;

import java.util.ArrayList;
import java.util.List;

public class DiskGenreDTO {

    private Integer id;

    private String name;

    private List<DiskDTO> disksList = new ArrayList<>();

    public DiskGenreDTO() {
    }

    public DiskGenreDTO(String name) {
        this.name = name;
    }

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

    public List<DiskDTO> getDisksList() {
        return disksList;
    }

    public void setDisksList(List<DiskDTO> disksList) {
        this.disksList = disksList;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiskGenreDTO)) return false;

        DiskGenreDTO that = (DiskGenreDTO) o;

        if (!getId().equals(that.getId())) return false;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

}