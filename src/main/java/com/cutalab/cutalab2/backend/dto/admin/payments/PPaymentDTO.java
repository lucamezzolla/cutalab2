package com.cutalab.cutalab2.backend.dto.admin.payments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PPaymentDTO implements Serializable {

    private Integer id;
    private String code;
    private String description;
    private String invoice;
    private String piva;
    private String pivaCode;
    private LocalDateTime paymentDate;
    private BigDecimal price;
    private String receipt;
    private PRegistryDTO registry;
    private PServiceDTO service;
    private PCurrencyDTO currency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getPivaCode() {
        return pivaCode;
    }

    public void setPivaCode(String pivaCode) {
        this.pivaCode = pivaCode;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public PRegistryDTO getRegistry() {
        return registry;
    }

    public void setRegistry(PRegistryDTO registry) {
        this.registry = registry;
    }

    public PServiceDTO getService() {
        return service;
    }

    public void setService(PServiceDTO service) {
        this.service = service;
    }

    public PCurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(PCurrencyDTO currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PPaymentDTO)) return false;

        PPaymentDTO that = (PPaymentDTO) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}