package com.cutalab.cutalab2.backend.entity.admin.payments;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "p_loan_returns")
public class PLoanReturnEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Transient
    private String formattedDate;

    @ManyToOne()
    @JoinColumn(referencedColumnName="id", name="p_loan_id", insertable=true, updatable=true, nullable=false)
    private PLoanEntity loan;

    @Column(name = "total", nullable = false)
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

    public PLoanEntity getLoan() {
        return loan;
    }

    public void setLoan(PLoanEntity loan) {
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
        if (!(o instanceof PLoanReturnEntity)) return false;
        PLoanReturnEntity that = (PLoanReturnEntity) o;
        return getId().equals(that.getId()) && getDate().equals(that.getDate()) && getLoan().equals(that.getLoan()) && getTotal().equals(that.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getLoan(), getTotal());
    }

}