package com.cutalab.cutalab2.backend.dto.admin.payments;

import com.cutalab.cutalab2.views.mycomponents.MyTextField;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class PCurrencyDTO implements Serializable {

    private Integer id;
    private String name;

    private final LinkedList<MyTextField> fields = new LinkedList<>();

    public LinkedList<MyTextField> getFields() {
        fields.clear();
        fields.add(new MyTextField(null, "Nome", "name", true));
        if(this.getId() != null && this.getId() > 0) {
            ((MyTextField) fields.get(0)).setValue(this.getName());
        }
        return fields;
    }

    public void setFields(LinkedList<MyTextField> fields) {
        if(fields.size() == 1) {
            this.setName(fields.get(0).getValue());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PCurrencyDTO)) return false;
        PCurrencyDTO that = (PCurrencyDTO) o;
        return getId().equals(that.getId()) && getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

}