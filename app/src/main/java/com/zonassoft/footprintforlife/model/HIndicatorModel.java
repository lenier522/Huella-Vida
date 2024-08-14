package com.zonassoft.footprintforlife.model;

/**
 * Class to save all values of indicator model.
 */
public class HIndicatorModel {
    public int id;
    public String indicator_code;
    public String country;
    public double value;
    public int year;

    public HIndicatorModel(int id, String indicator_code, String country, double value, int year) {
        this.id = id;
        this.indicator_code = indicator_code;
        this.country = country;
        this.value = value;
        this.year = year;
    }

    public HIndicatorModel() {
    }

    protected double value(int year, String code) {
        return 0;
    }
}
