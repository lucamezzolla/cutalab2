package com.cutalab.cutalab2.backend.dto;

import com.cutalab.cutalab2.backend.entity.AreaLinkEntity;

public class LinkDTO {

    private Integer id;

    private String title;

    private String url;

    private AreaLinkDTO areaLinkDTO;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AreaLinkDTO getAreaLinkDTO() {
        return areaLinkDTO;
    }

    public void setAreaLinkDTO(AreaLinkDTO areaLinkDTO) {
        this.areaLinkDTO = areaLinkDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkDTO)) return false;

        LinkDTO linkDTO = (LinkDTO) o;

        return getId().equals(linkDTO.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return title;
    }

}