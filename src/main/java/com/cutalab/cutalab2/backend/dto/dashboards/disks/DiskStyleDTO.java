package com.cutalab.cutalab2.backend.dto.dashboards.disks;

import java.util.ArrayList;
import java.util.List;

public class DiskStyleDTO {


    private Integer id;

    private String name;

    public DiskStyleDTO() {
    }

    public DiskStyleDTO(String name) {
        this.name = name;
    }

    private List<DiskStyleDTO> disksList = new ArrayList<>();

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

    public List<DiskStyleDTO> getDisksList() {
        return disksList;
    }

    public void setDisksList(List<DiskStyleDTO> disksList) {
        this.disksList = disksList;
    }

    @Override
    public String toString() {
        return name;
    }

}