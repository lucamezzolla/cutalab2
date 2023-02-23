package com.cutalab.cutalab2.backend.dto;

import com.cutalab.cutalab2.backend.entity.LinkEntity;

import java.util.List;

public class AreaLinkDTO {

    private Integer id;


    private String title;


    private List<LinkEntity> links;

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

    public List<LinkEntity> getLinks() {
        return links;
    }

    public void setLinks(List<LinkEntity> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return title;
    }

}