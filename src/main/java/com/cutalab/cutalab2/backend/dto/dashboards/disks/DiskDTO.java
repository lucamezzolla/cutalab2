package com.cutalab.cutalab2.backend.dto.dashboards.disks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DiskDTO {

    private Integer id;

    private String title;

    private String author;

    private String year;

    private String reprint;

    private String label;

    private BigDecimal presumedValue;

    private boolean openable = true;

    private List<DiskGenreDTO> diskGenreList = new ArrayList<>();

    private List<DiskStyleDTO> diskStyleList = new ArrayList<>();

    private StatusDTO coverStatus;

    private StatusDTO diskStatus;

    private Integer userId;

    private String note;

    private String cover;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReprint() {
        return reprint;
    }

    public void setReprint(String reprint) {
        this.reprint = reprint;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getPresumedValue() {
        return presumedValue;
    }

    public void setPresumedValue(BigDecimal presumedValue) {
        this.presumedValue = presumedValue;
    }

    public boolean isOpenable() {
        return openable;
    }

    public void setOpenable(boolean openable) {
        this.openable = openable;
    }

    public List<DiskGenreDTO> getDiskGenreList() {
        return diskGenreList;
    }

    public void setDiskGenreList(List<DiskGenreDTO> diskGenreList) {
        this.diskGenreList = diskGenreList;
    }

    public List<DiskStyleDTO> getDiskStyleList() {
        return diskStyleList;
    }

    public void setDiskStyleList(List<DiskStyleDTO> diskStyleList) {
        this.diskStyleList = diskStyleList;
    }

    public StatusDTO getCoverStatus() {
        return coverStatus;
    }

    public void setCoverStatus(StatusDTO coverStatus) {
        this.coverStatus = coverStatus;
    }

    public StatusDTO getDiskStatus() {
        return diskStatus;
    }

    public void setDiskStatus(StatusDTO diskStatus) {
        this.diskStatus = diskStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiskDTO)) return false;

        DiskDTO diskDTO = (DiskDTO) o;

        return getId().equals(diskDTO.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "DiskDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", reprint='" + reprint + '\'' +
                ", label='" + label + '\'' +
                ", presumedValue=" + presumedValue +
                ", openable=" + openable +
                ", diskGenreList=" + diskGenreList +
                ", diskStyleList=" + diskStyleList +
                ", coverStatus=" + coverStatus +
                ", diskStatus=" + diskStatus +
                ", userId=" + userId +
                ", note='" + note + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
