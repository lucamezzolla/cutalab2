package com.cutalab.cutalab2.backend.dto.admin.payments;

import com.cutalab.cutalab2.views.mycomponents.MyTextField;

import java.io.Serializable;
import java.util.*;

public class PRegistryDTO implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String cap;

    private String city;

    private String cityCode;

    private String country;

    private String fiscalCode;

    private String piva;

    private String phone;

    private String mobile;

    private String email;

    private String note;

    private final LinkedList<MyTextField> fields = new LinkedList<>();

    public LinkedList<MyTextField> getFields() {
        fields.clear();
        fields.add(new MyTextField(null, "Nome", "name", true));
        fields.add(new MyTextField(null, "Indirizzo", "address", false));
        fields.add(new MyTextField(null, "CAP", "cap", false));
        fields.add(new MyTextField(null, "Città", "city", false));
        fields.add(new MyTextField(null, "Provincia", "cityCode", false));
        fields.add(new MyTextField(null, "Paese", "country", false));
        fields.add(new MyTextField(null, "Email", "email", false));
        fields.add(new MyTextField(null, "Codice fiscale", "fiscalCode", false));
        fields.add(new MyTextField(null, "Cellulare", "mobile", false));
        fields.add(new MyTextField(null, "Telefono", "phone", false));
        fields.add(new MyTextField(null, "Partita IVA", "piva", false));
        fields.add(new MyTextField(null, "Note", "note", false));
        if(this.getId() != null && this.getId() > 0) {
            ((MyTextField) fields.get(0)).setValue(this.getName());
            ((MyTextField) fields.get(1)).setValue(this.getAddress());
            ((MyTextField) fields.get(2)).setValue(this.getCap());
            ((MyTextField) fields.get(3)).setValue(this.getCity());
            ((MyTextField) fields.get(4)).setValue(this.getCityCode());
            ((MyTextField) fields.get(5)).setValue(this.getCountry());
            ((MyTextField) fields.get(6)).setValue(this.getEmail());
            ((MyTextField) fields.get(7)).setValue(this.getFiscalCode());
            ((MyTextField) fields.get(8)).setValue(this.getMobile());
            ((MyTextField) fields.get(9)).setValue(this.getPhone());
            ((MyTextField) fields.get(10)).setValue(this.getPiva());
            ((MyTextField) fields.get(11)).setValue(this.getNote());
        }
        return fields;
    }

    public void setFields(LinkedList<MyTextField> fields) {
        if(fields.size() == 12) {
            this.setName(fields.get(0).getValue());
            this.setAddress(fields.get(1).getValue());
            this.setCap(fields.get(2).getValue());
            this.setCity(fields.get(3).getValue());
            this.setCityCode(fields.get(4).getValue());
            this.setCountry(fields.get(5).getValue());
            this.setEmail(fields.get(6).getValue());
            this.setFiscalCode(fields.get(7).getValue());
            this.setMobile(fields.get(8).getValue());
            this.setPhone(fields.get(9).getValue());
            this.setPiva(fields.get(10).getValue());
            this.setNote(fields.get(11).getValue());
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

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PRegistryDTO)) return false;
        PRegistryDTO that = (PRegistryDTO) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}