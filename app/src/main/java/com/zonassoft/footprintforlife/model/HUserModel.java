package com.zonassoft.footprintforlife.model;

import java.io.Serializable;

/**
 * Class to save all values of user model.
 */

public class HUserModel implements Serializable {


    public String name;
    public int age;
    public boolean intro;
    public String country;
    public int id_country;

    public HUserModel(String name, int age, String country, int id_country) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.id_country = id_country;
        this.intro=true;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }

    public int getYear() {
        return (2020 - age);
    }

    public boolean isIntro() {
        return intro;
    }

    public void setIntro(boolean intro) {
        this.intro = intro;
    }
}
