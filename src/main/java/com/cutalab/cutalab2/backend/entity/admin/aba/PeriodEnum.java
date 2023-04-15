package com.cutalab.cutalab2.backend.entity.admin.aba;

import java.util.ArrayList;
import java.util.List;

public enum PeriodEnum {

    ZERO("ZERO", "zero"),
    MONTH("MONTH", "mensile"),
    BIMONTHLY("BIMONTHLY", "bimestrale"),
    TREEMONTH("TREEMONTH", "trimestrale"),
    QUARTERLY("QUARTERLY", "quadrimestrale"),
    FIVEMONTH("FIVEMONTH", "pentamestrale"),
    SEMIANNUAL("SEMIANNUAL", "semestrale"),
    ANNUAL("ANNUAL", "annuale");

    private final String key;
    private final String value;

    PeriodEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    public List<PeriodEnum> getList() {
        List<PeriodEnum> periods = new ArrayList<>();
        periods.add(MONTH);
        periods.add(BIMONTHLY);
        periods.add(TREEMONTH);
        periods.add(QUARTERLY);
        periods.add(FIVEMONTH);
        periods.add(SEMIANNUAL);
        periods.add(ANNUAL);
        return periods;
    }

    @Override
    public String toString() {
        return value;
    }
}