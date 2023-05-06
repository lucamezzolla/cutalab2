package com.cutalab.cutalab2.backend.entity.admin.aba;

import com.cutalab.cutalab2.backend.entity.admin.payments.PPaymentEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="aba_packages")
public class ABAPackageEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false)
    private PeriodEnum period;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "payment_id", nullable = false)
    private PPaymentEntity payment;

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

    public PPaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PPaymentEntity payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ABAPackageEntity)) return false;
        ABAPackageEntity that = (ABAPackageEntity) o;
        return getId().equals(that.getId()) && Objects.equals(getHours(), that.getHours()) && getPeriod() == that.getPeriod() && Objects.equals(isOpen, that.isOpen) && getPayment().equals(that.getPayment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHours(), getPeriod(), isOpen, getPayment());
    }

}