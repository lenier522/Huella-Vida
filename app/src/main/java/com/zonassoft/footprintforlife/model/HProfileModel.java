package com.zonassoft.footprintforlife.model;

/**
 * Class to save all values of questions profile.
 */

public class HProfileModel {

    //general profile infomation
    public int id;
    public String name;
    public String country;
    public int id_country;
    public int year;

    //transport profile infomation
    public double t_kms_by_car;
    public int t_persons_cars;
    public double t_kms_by_motobike;
    public double t_kms_by_bus;
    public double t_kms_by_train;

    //flighs profile information
    public double v_kms_by_airplane;

    //electricity profile information
    public double e_kwatts_custom;
    public int e_kwatts_aux;
    public double e_kwatts_r;

    //home profile information
    public double h_fuel_gas_custom;
    public int h_fuel_gas_aux;
    public double h_kgrs_wood;
    public int h_number_peoples;

    //diet profile information
    public int d_type;
    public double d_local;

    //incomes profile information
    public double g_avg;
    public double g_individual;
    public double g_dep;


    public HProfileModel(int id, String name, String country, int id_country, int year) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.id_country = id_country;
        this.year = year;
        this.t_persons_cars = 1;
        this.e_kwatts_aux = 5;
        this.h_fuel_gas_aux = 5;
    }

    public HProfileModel() {
        this.id = 1;
        this.t_persons_cars = 1;
        this.e_kwatts_aux = 5;
        this.h_fuel_gas_aux = 5;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", id_country='" + id_country + '\'' +
                ", year=" + year +
                ", t_kms_by_car=" + t_kms_by_car +
                ", t_persons_cars=" + t_persons_cars +
                ", t_kms_by_motobike=" + t_kms_by_motobike +
                ", t_kms_by_bus=" + t_kms_by_bus +
                ", t_kms_by_train=" + t_kms_by_train +
                ", v_kms_by_airplane=" + v_kms_by_airplane +
                ", e_kwatts_custom=" + e_kwatts_custom +
                ", e_kwatts_aux=" + e_kwatts_aux +
                ", e_kwatts_r=" + e_kwatts_r +
                ", h_fuel_gas_custom=" + h_fuel_gas_custom +
                ", h_fuel_gas_aux=" + h_fuel_gas_aux +
                ", h_kgrs_wood=" + h_kgrs_wood +
                ", h_number_peoples=" + h_number_peoples +
                ", d_type='" + d_type + '\'' +
                ", d_local=" + d_local +
                ", g_avg=" + g_avg +
                ", g_individual=" + g_individual +
                '}';
    }
}
