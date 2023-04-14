package com.cutalab.cutalab2.backend.entity.admin.payments;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "p_loans")
public class PLoanEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne()
    @JoinColumn(referencedColumnName="id", name="p_registry_id", insertable=true, updatable=true, nullable=false)
    private PRegistryEntity registry;

    @OneToOne()
    @JoinColumn(referencedColumnName="id", name="p_currency_id", insertable=true, updatable=true, nullable=false)
    private PCurrencyEntity currency;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Transient
    private String loanFormattedDate;

    @Transient
    private BigDecimal loanReturnTotal;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PRegistryEntity getRegistry() {
        return registry;
    }

    public void setRegistry(PRegistryEntity registry) {
        this.registry = registry;
    }

    public PCurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(PCurrencyEntity currency) {
        this.currency = currency;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getLoanFormattedDate() {
        return loanFormattedDate;
    }

    public void setLoanFormattedDate(String loanFormattedDate) {
        this.loanFormattedDate = loanFormattedDate;
    }

    public BigDecimal getLoanReturnTotal() {
        return loanReturnTotal;
    }

    public void setLoanReturnTotal(BigDecimal loanReturnTotal) {
        this.loanReturnTotal = loanReturnTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PLoanEntity)) return false;
        PLoanEntity that = (PLoanEntity) o;
        return getId().equals(that.getId()) && getRegistry().equals(that.getRegistry()) && getCurrency().equals(that.getCurrency()) && getLoanDate().equals(that.getLoanDate()) && getTotal().equals(that.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRegistry(), getCurrency(), getLoanDate(), getTotal());
    }

}