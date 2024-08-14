package com.zonassoft.footprintforlife.room.table;



/*
 * To save favorites news
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.zonassoft.footprintforlife.model.HIndicatorModel;


@Entity(tableName = "IndicatorEntity")
public class IndicatorEntity {
    @PrimaryKey
    private int id = -1;

    @ColumnInfo(name = "code")
    private String code = "";

    @ColumnInfo(name = "country")
    private String country = "";

    @ColumnInfo(name = "value")
    private double value = 0;

    @ColumnInfo(name = "year")
    private int year = 0;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static IndicatorEntity entity(HIndicatorModel n) {
        IndicatorEntity entity = new IndicatorEntity();
        entity.setId(n.id);
        entity.setCountry(n.country);
        entity.setCode(n.indicator_code);
        entity.setValue(n.value);
        entity.setYear(n.year);
        return entity;
    }

    public HIndicatorModel original() {
        HIndicatorModel n = new HIndicatorModel();
        n.indicator_code = getCode();
        n.value = getValue();
        n.year = getYear();
        n.country = getCountry();
        n.id = getId();
        return n;
    }

    @Override
    public String toString() {
        return "IndicatorEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", country='" + country + '\'' +
                ", value=" + value +
                ", year=" + year +
                '}';
    }
}
