package com.cutalab.cutalab2.backend.dto.admin.aba;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ABASessionDTO implements Serializable {

    private Integer id;
    private Integer hours;
    private LocalDateTime day;
    private Boolean isOpen;
    private String paymentFormattedDate;
    private ABAPackageDTO ABAPackage;

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

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getPaymentFormattedDate() {
        return paymentFormattedDate;
    }

    public void setPaymentFormattedDate(String paymentFormattedDate) {
        this.paymentFormattedDate = paymentFormattedDate;
    }

    public ABAPackageDTO getABAPackage() {
        return ABAPackage;
    }

    public void setABAPackage(ABAPackageDTO ABAPackage) {
        this.ABAPackage = ABAPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ABASessionDTO)) return false;

        ABASessionDTO that = (ABASessionDTO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}