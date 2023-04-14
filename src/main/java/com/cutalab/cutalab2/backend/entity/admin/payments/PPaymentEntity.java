package com.cutalab.cutalab2.backend.entity.admin.payments;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "p_payments")
public class PPaymentEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "invoice")
    private String invoice;

    @Column(name = "piva")
    private String piva;

    @Column(name = "piva_code")
    private String pivaCode;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Transient
    private String paymentFormattedDate;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "receipt")
    private String receipt;
    @OneToOne()
    @JoinColumn(referencedColumnName="id", name="p_registry_id", insertable=true, updatable=true, nullable=false)
    private PRegistryEntity registry;

    @OneToOne()
    @JoinColumn(referencedColumnName="id", name="p_service_id", insertable=true, updatable=true, nullable=false)
    private PServiceEntity service;

    @OneToOne()
    @JoinColumn(referencedColumnName="id", name="p_currency_id", insertable=true, updatable=true, nullable=false)
    private PCurrencyEntity currency;

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

    public PRegistryEntity getRegistry() {
        return registry;
    }

    public void setRegistry(PRegistryEntity registry) {
        this.registry = registry;
    }

    public PServiceEntity getService() {
        return service;
    }

    public void setService(PServiceEntity service) {
        this.service = service;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PCurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(PCurrencyEntity currency) {
        this.currency = currency;
    }

    public String getPaymentFormattedDate() {
        return paymentFormattedDate;
    }

    public void setPaymentFormattedDate(String paymentFormattedDate) {
        this.paymentFormattedDate = paymentFormattedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PPaymentEntity)) return false;
        PPaymentEntity that = (PPaymentEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "PPaymentModel{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", invoice='" + invoice + '\'' +
                ", piva='" + piva + '\'' +
                ", pivaCode='" + pivaCode + '\'' +
                ", paymentDate=" + paymentDate +
                ", price=" + price +
                ", receipt='" + receipt + '\'' +
                ", registry=" + registry +
                ", service=" + service +
                ", currency=" + currency +
                '}';
    }

}