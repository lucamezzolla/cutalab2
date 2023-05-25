package com.cutalab.cutalab2.backend.dto.admin.payments;

import com.cutalab.cutalab2.views.mycomponents.MyTextField;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class PServiceDTO implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String description;

    private String fiscalCode;

    private String piva;

    private final LinkedList<MyTextField> fields = new LinkedList<>();

    public LinkedList<MyTextField> getFields() {
        fields.clear();
        fields.add(new MyTextField(null, "Nome", "name", true));
        fields.add(new MyTextField(null, "Indirizzo", "address", false));
        fields.add(new MyTextField(null, "Descrizione", "description", false));
        fields.add(new MyTextField(null, "Codice fiscale", "fiscalCode", false));
        fields.add(new MyTextField(null, "Partita IVA", "piva", false));
        if(this.getId() != null && this.getId() > 0) {
            ((MyTextField) fields.get(0)).setValue(this.getName());
            ((MyTextField) fields.get(1)).setValue(this.getAddress());
            ((MyTextField) fields.get(2)).setValue(this.getDescription());
            ((MyTextField) fields.get(3)).setValue(this.getFiscalCode());
            ((MyTextField) fields.get(4)).setValue(this.getPiva());
        }
        return fields;
    }

    public void setFields(LinkedList<MyTextField> fields) {
        if(fields.size() == 5) {
            this.setName(fields.get(0).getValue());
            this.setAddress(fields.get(1).getValue());
            this.setDescription(fields.get(2).getValue());
            this.setFiscalCode(fields.get(3).getValue());
            this.setPiva(fields.get(4).getValue());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PServiceDTO)) return false;
        PServiceDTO that = (PServiceDTO) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}