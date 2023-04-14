package com.cutalab.cutalab2.backend.dto.admin.payments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class PPaymentPartDTO implements Serializable {


    private Integer id;


    private Integer paymentId;


    private BigDecimal price;


    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PPaymentPartDTO)) return false;
        PPaymentPartDTO that = (PPaymentPartDTO) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}