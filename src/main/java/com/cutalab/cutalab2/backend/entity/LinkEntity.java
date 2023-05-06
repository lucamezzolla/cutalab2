package com.cutalab.cutalab2.backend.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class LinkEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String title;

    private String url;

    @OneToOne()
    @JoinColumn(referencedColumnName="id", name = "area_link_id", nullable=false)
    private AreaLinkEntity areaLinkEntity;

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

    public AreaLinkEntity getAreaLinkEntity() {
        return areaLinkEntity;
    }

    public void setAreaLinkEntity(AreaLinkEntity areaLinkEntity) {
        this.areaLinkEntity = areaLinkEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkEntity)) return false;

        LinkEntity that = (LinkEntity) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}