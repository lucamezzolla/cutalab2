package com.cutalab.cutalab2.backend.entity.admin.aba;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "aba_sessions")
public class ABASessionEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    @Column(name = "day", nullable = false)
    private LocalDateTime day;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen;

    @Transient
    private String paymentFormattedDate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "package_id", insertable = true, updatable = true, nullable = false)
    private ABAPackageEntity ABAPackage;

    public String getPaymentFormattedDate() {
        return paymentFormattedDate;
    }

    public void setPaymentFormattedDate(String paymentFormattedDate) {
        this.paymentFormattedDate = paymentFormattedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public ABAPackageEntity getABAPackage() {
        return ABAPackage;
    }

    public void setABAPackage(ABAPackageEntity ABAPackage) {
        this.ABAPackage = ABAPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ABASessionEntity)) return false;
        ABASessionEntity that = (ABASessionEntity) o;
        return getId().equals(that.getId()) && Objects.equals(getHours(), that.getHours()) && Objects.equals(getDay(), that.getDay()) && Objects.equals(isOpen, that.isOpen) && getABAPackage().equals(that.getABAPackage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHours(), getDay(), isOpen, getABAPackage());
    }

}