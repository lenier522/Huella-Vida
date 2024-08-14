package com.zonassoft.footprintforlife.room.table;



/*
 * To save Profile data
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.zonassoft.footprintforlife.model.HProfileModel;


@Entity(tableName = "ProfileEntity")
public class ProfileEntity {
    @PrimaryKey
    private int id = -1;

    @ColumnInfo(name = "nombre")
    private String nombre = "";

    @ColumnInfo(name = "country")
    private String country = "";

    @ColumnInfo(name = "id_country")
    private int id_country = 0;

    @ColumnInfo(name = "year")
    private int year = 0;

    @ColumnInfo(name = "t_kms_by_car")
    private double t_kms_by_car = 0;

    @ColumnInfo(name = "t_persons_cars")
    private int t_persons_cars = 0;

    @ColumnInfo(name = "t_kms_by_motobike")
    private double t_kms_by_motobike = 0;

    @ColumnInfo(name = "t_kms_by_bus")
    private double t_kms_by_bus = 0;

    @ColumnInfo(name = "t_kms_by_train")
    private double t_kms_by_train = 0;

    @ColumnInfo(name = "v_kms_by_airplane")
    private double v_kms_by_airplane = 0;

    @ColumnInfo(name = "e_kwatts_custom")
    private double e_kwatts_custom = 0;

    @ColumnInfo(name = "e_kwatts_aux")
    private int e_kwatts_aux = 0;

    @ColumnInfo(name = "e_kwatts_r")
    private double e_kwatts_r = 0;

    @ColumnInfo(name = "h_fuel_gas_custom")
    private double h_fuel_gas_custom = 0;

    @ColumnInfo(name = "h_fuel_gas_aux")
    private int h_fuel_gas_aux = 0;

    @ColumnInfo(name = "h_kgrs_wood")
    private double h_kgrs_wood = 0;

    @ColumnInfo(name = "h_number_peoples")
    private int h_number_peoples = 0;

    @ColumnInfo(name = "d_type")
    private int d_type = 0;

    @ColumnInfo(name = "d_local")
    private double d_local = 0;

    @ColumnInfo(name = "g_avg")
    private double g_avg = 0;

    @ColumnInfo(name = "g_individual")
    private double g_individual = 0;

    @ColumnInfo(name = "g_dep")
    private double g_dep = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getT_kms_by_car() {
        return t_kms_by_car;
    }

    public void setT_kms_by_car(double t_kms_by_car) {
        this.t_kms_by_car = t_kms_by_car;
    }

    public int getT_persons_cars() {
        return t_persons_cars;
    }

    public void setT_persons_cars(int t_persons_cars) {
        this.t_persons_cars = t_persons_cars;
    }

    public double getT_kms_by_motobike() {
        return t_kms_by_motobike;
    }

    public void setT_kms_by_motobike(double t_kms_by_motobike) {
        this.t_kms_by_motobike = t_kms_by_motobike;
    }

    public double getG_dep() {
        return g_dep;
    }

    public void setG_dep(double g_dep) {
        this.g_dep = g_dep;
    }

    public double getT_kms_by_bus() {
        return t_kms_by_bus;
    }

    public void setT_kms_by_bus(double t_kms_by_bus) {
        this.t_kms_by_bus = t_kms_by_bus;
    }

    public double getT_kms_by_train() {
        return t_kms_by_train;
    }

    public void setT_kms_by_train(double t_kms_by_train) {
        this.t_kms_by_train = t_kms_by_train;
    }

    public double getV_kms_by_airplane() {
        return v_kms_by_airplane;
    }

    public void setV_kms_by_airplane(double v_kms_by_airplane) {
        this.v_kms_by_airplane = v_kms_by_airplane;
    }

    public double getE_kwatts_custom() {
        return e_kwatts_custom;
    }

    public void setE_kwatts_custom(double e_kwatts_custom) {
        this.e_kwatts_custom = e_kwatts_custom;
    }

    public int getE_kwatts_aux() {
        return e_kwatts_aux;
    }

    public void setE_kwatts_aux(int e_kwatts_aux) {
        this.e_kwatts_aux = e_kwatts_aux;
    }

    public double getE_kwatts_r() {
        return e_kwatts_r;
    }

    public void setE_kwatts_r(double e_kwatts_r) {
        this.e_kwatts_r = e_kwatts_r;
    }

    public double getH_fuel_gas_custom() {
        return h_fuel_gas_custom;
    }

    public void setH_fuel_gas_custom(double h_fuel_gas_custom) {
        this.h_fuel_gas_custom = h_fuel_gas_custom;
    }

    public int getH_fuel_gas_aux() {
        return h_fuel_gas_aux;
    }

    public void setH_fuel_gas_aux(int h_fuel_gas_aux) {
        this.h_fuel_gas_aux = h_fuel_gas_aux;
    }

    public double getH_kgrs_wood() {
        return h_kgrs_wood;
    }

    public void setH_kgrs_wood(double h_kgrs_wood) {
        this.h_kgrs_wood = h_kgrs_wood;
    }

    public int getH_number_peoples() {
        return h_number_peoples;
    }

    public void setH_number_peoples(int h_number_peoples) {
        this.h_number_peoples = h_number_peoples;
    }

    public int getD_type() {
        return d_type;
    }

    public void setD_type(int d_type) {
        this.d_type = d_type;
    }

    public double getD_local() {
        return d_local;
    }

    public void setD_local(double d_local) {
        this.d_local = d_local;
    }

    public double getG_avg() {
        return g_avg;
    }

    public void setG_avg(double g_avg) {
        this.g_avg = g_avg;
    }

    public double getG_individual() {
        return g_individual;
    }

    public void setG_individual(double g_individual) {
        this.g_individual = g_individual;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }

    public static ProfileEntity entity(HProfileModel n) {
        ProfileEntity entity = new ProfileEntity();
        //profile info
        entity.setId(n.id);
        entity.setNombre(n.name);
        entity.setCountry(n.country);
        entity.setId_country(n.id_country);
        entity.setYear(n.year);
        //transport
        entity.setT_kms_by_car(n.t_kms_by_car);
        entity.setT_kms_by_bus(n.t_kms_by_bus);
        entity.setT_kms_by_motobike(n.t_kms_by_motobike);
        entity.setT_persons_cars(n.t_persons_cars);
        entity.setT_kms_by_train(n.t_kms_by_train);
        //vuelos
        entity.setV_kms_by_airplane(n.v_kms_by_airplane);
        //casa
        entity.setH_fuel_gas_aux(n.h_fuel_gas_aux);
        entity.setH_fuel_gas_custom(n.h_fuel_gas_custom);
        entity.setH_kgrs_wood(n.h_kgrs_wood);
        entity.setH_number_peoples(n.h_number_peoples);
        //electicity
        entity.setE_kwatts_aux(n.e_kwatts_aux);
        entity.setE_kwatts_custom(n.e_kwatts_custom);
        entity.setE_kwatts_r(n.e_kwatts_r);
        //gastos
        entity.setG_avg(n.g_avg);
        entity.setG_dep(n.g_dep);
        entity.setG_individual(n.g_individual);
        //dieta
        entity.setD_local(n.d_local);
        entity.setD_type(n.d_type);

        return entity;
    }

    public HProfileModel original() {

        HProfileModel n = new HProfileModel();
        n.id = getId();
        n.name = getNombre();
        n.country = getCountry();
        n.id_country = getId_country();
        n.year = getYear();
        n.t_kms_by_bus = getT_kms_by_bus();
        n.t_kms_by_car = getT_kms_by_car();
        n.t_kms_by_motobike = getT_kms_by_motobike();
        n.t_kms_by_train = getT_kms_by_train();
        n.t_persons_cars = getT_persons_cars();

        n.v_kms_by_airplane = getV_kms_by_airplane();

        n.h_fuel_gas_aux = getH_fuel_gas_aux();
        n.h_fuel_gas_custom = getH_fuel_gas_custom();
        n.h_kgrs_wood = getH_kgrs_wood();
        n.h_number_peoples = getH_number_peoples();

        n.e_kwatts_aux = getE_kwatts_aux();
        n.e_kwatts_custom = getE_kwatts_custom();
        n.e_kwatts_r = getE_kwatts_r();

        n.g_avg = getG_avg();
        n.g_dep = getG_dep();
        n.g_individual = getG_individual();

        n.d_local = getD_local();
        n.d_type = getD_type();

        return n;
    }
}
