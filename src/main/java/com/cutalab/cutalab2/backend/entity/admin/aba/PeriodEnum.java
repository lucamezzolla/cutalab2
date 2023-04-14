package com.cutalab.cutalab2.backend.entity.admin.aba;

public enum PeriodEnum {

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

}