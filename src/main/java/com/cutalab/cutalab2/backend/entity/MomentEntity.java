package com.cutalab.cutalab2.backend.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "moments")
public class MomentEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "body", columnDefinition = "longtext")
    private String body;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "googledriveid")
    private String googledriveid;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getGoogledriveid() {
        return googledriveid;
    }

    public void setGoogledriveid(String googledriveid) {
        this.googledriveid = googledriveid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MomentEntity)) return false;

        MomentEntity that = (MomentEntity) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}