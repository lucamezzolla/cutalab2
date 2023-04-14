package com.cutalab.cutalab2.backend.dto.admin.payments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PLoanDTO implements Serializable {

    private Integer id;
    private PRegistryDTO registry;
    private PCurrencyDTO currency;
    private LocalDate loanDate;
    private String loanFormattedDate;
    private BigDecimal loanReturnTotal;
    private BigDecimal total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PRegistryDTO getRegistry() {
        return registry;
    }

    public void setRegistry(PRegistryDTO registry) {
        this.registry = registry;
    }

    public PCurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(PCurrencyDTO currency) {
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
        if (!(o instanceof PLoanDTO)) return false;
        PLoanDTO that = (PLoanDTO) o;
        return getId().equals(that.getId()) && getRegistry().equals(that.getRegistry()) && getCurrency().equals(that.getCurrency()) && getLoanDate().equals(that.getLoanDate()) && getTotal().equals(that.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRegistry(), getCurrency(), getLoanDate(), getTotal());
    }

}