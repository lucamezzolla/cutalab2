package com.cutalab.cutalab2.backend.entity.dashboards.disks;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "disks")
public class DiskEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, unique = false, length = 80)
    private String title;

    @Column(name = "author", nullable = false, length = 80)
    private String author;

    @Column(name = "year", nullable = false, length = 4)
    private String year;

    @Column(name = "reprint", nullable = false, length = 25)
    private String reprint;

    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Column(name = "presumed_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal presumedValue;

    @Column(name = "openable", nullable = false)
    private boolean openable = true;

    @ManyToMany(mappedBy = "disksList")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<DiskGenreEntity> diskGenreList = new ArrayList<>();

    @ManyToMany(mappedBy = "disksList")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<DiskStyleEntity> diskStyleList = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(referencedColumnName="id", name="cover_status", insertable=true, updatable=true, nullable=false)
    private StatusEntity coverStatus;

    @ManyToOne()
    @JoinColumn(referencedColumnName="id", name="disk_status", insertable=true, updatable=true, nullable=false)
    private StatusEntity diskStatus;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name="note", columnDefinition="LONGTEXT", nullable = true)
    private String note;

    @Column(name = "cover", nullable = true, length = 255)
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

    public void addGenre(DiskGenreEntity DiskGenreEntity) {
        if(!diskGenreList.contains(DiskGenreEntity)) {
            diskGenreList.add(DiskGenreEntity);
            DiskGenreEntity.getDisksList().add(this);
        }
    }

    public void removeGenre(DiskGenreEntity DiskGenreEntity) {
        diskGenreList.remove(DiskGenreEntity);
        DiskGenreEntity.setDisksList(null);
    }

    public void addStyle(DiskStyleEntity DiskStyleEntity) {
        if(!diskStyleList.contains(DiskStyleEntity)) {
            diskStyleList.add(DiskStyleEntity);
            DiskStyleEntity.getDisksList().add(this);
        }
    }

    public void removeStyle(DiskStyleEntity DiskStyleEntity) {
        diskGenreList.remove(DiskStyleEntity);
        DiskStyleEntity.setDisksList(null);
    }

    public List<DiskGenreEntity> getDiskGenreList() {
        return diskGenreList;
    }

    public void setDiskGenreList(List<DiskGenreEntity> diskGenreList) {
        this.diskGenreList = diskGenreList;
    }

    public StatusEntity getCoverStatus() {
        return coverStatus;
    }

    public void setCoverStatus(StatusEntity coverStatus) {
        this.coverStatus = coverStatus;
    }

    public StatusEntity getDiskStatus() {
        return diskStatus;
    }

    public void setDiskStatus(StatusEntity diskStatus) {
        this.diskStatus = diskStatus;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<DiskStyleEntity> getDiskStyleList() {
        return diskStyleList;
    }

    public void setDiskStyleList(List<DiskStyleEntity> diskStyleList) {
        this.diskStyleList = diskStyleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiskEntity)) return false;
        DiskEntity DiskEntity = (DiskEntity) o;
        return getId().equals(DiskEntity.getId()) && getTitle().equals(DiskEntity.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

}