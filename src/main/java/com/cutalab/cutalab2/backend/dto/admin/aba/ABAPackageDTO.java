package com.cutalab.cutalab2.backend.dto.admin.aba;

import com.cutalab.cutalab2.backend.entity.admin.aba.PeriodEnum;
import com.cutalab.cutalab2.backend.dto.admin.payments.PPaymentDTO;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class ABAPackageDTO implements Serializable {

    private Integer id;
    private Integer hours;
    private PeriodEnum period;
    private Boolean isOpen;
    private PPaymentDTO payment;

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

    public PeriodEnum getPeriod() {
        return period;
    }

    public void setPeriod(PeriodEnum period) {
        this.period = period;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getPaymentFormattedDate() {
        if(payment.getPaymentDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy (HH:mm)");
            return payment.getPaymentDate().format(formatter);
        }
        return "";
    }

    public String getPeriodFormatted() {
        return period.getValue();
    }

    public PPaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PPaymentDTO payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ABAPackageDTO)) return false;

        ABAPackageDTO that = (ABAPackageDTO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}