package com.cutalab.cutalab2.backend.dto.admin.payments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PLoanReturnDTO implements Serializable {

    private Integer id;


    private LocalDate date;


    private String formattedDate;

    private PLoanDTO loan;

    private BigDecimal total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PLoanDTO getLoan() {
        return loan;
    }

    public void setLoan(PLoanDTO loan) {
        this.loan = loan;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PLoanReturnDTO)) return false;
        PLoanReturnDTO that = (PLoanReturnDTO) o;
        return getId().equals(that.getId()) && getDate().equals(that.getDate()) && getLoan().equals(that.getLoan()) && getTotal().equals(that.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getLoan(), getTotal());
    }

}