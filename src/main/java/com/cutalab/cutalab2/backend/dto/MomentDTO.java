package com.cutalab.cutalab2.backend.dto;

public class MomentDTO {

    private Integer id;

    private String title;

    private String subtitle;

    private String body;

    private Integer position;

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
        if (!(o instanceof MomentDTO)) return false;

        MomentDTO momentDTO = (MomentDTO) o;

        return getId().equals(momentDTO.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}