package com.zonassoft.footprintforlife.model;


import com.zonassoft.footprintforlife.data.Constant;

import java.io.Serializable;

/**
 * Class to save all values of footprint calculator results.
 */

public class HResults implements Serializable {

    //personal results
    public double em_pcy;
    public double p_c_em;
    public double le;
    public double lifetime;
    public double by_car;
    public double by_motobike;
    public double by_bus;
    public double by_airplane;
    public double by_train;
    public double by_electricity;
    public double by_heating;
    public double by_diet;
    public double by_manufacture;
    public double income;

    //world results
    public double w_em_pcy;
    public double w_p_c_em;
    public double w_le;
    public double w_lifetime;
    public double w_by_transport;
    public double w_by_electricity;
    public double w_by_heating;
    public double w_by_diet;
    public double w_by_manufacture;
    public double w_income;

    //country results
    public double c_em_pcy;
    public double c_p_c_em;
    public double c_le;
    public double c_lifetime;
    public double c_by_transport;
    public double c_by_electricity;
    public double c_by_heating;
    public double c_by_diet;
    public double c_by_manufacture;
    public double c_income;

    //global values
    public double total_lyt;
    public double future_lyt;

    //etical results
    public double e_em_pcy;
    public double e_p_c_em;
    public double e_le;
    public double e_lifetime;
    public double e_by_transport;
    public double e_by_electricity;
    public double e_by_heating;
    public double e_by_diet;
    public double e_by_manufacture;
    public double e_income;


    @Override
    public String toString() {
        return "Calculator='" + '\'' +
                ", \nem_pcy='" + em_pcy + '\'' +
                ", \np_c_em='" + p_c_em + '\'' +
                ", \nle='" + le + '\'' +
                ", \nlifetime='" + lifetime + '\'' +
                ", \nby_car='" + by_car + '\'' +
                ", \nby_motobike='" + by_motobike + '\'' +
                ", \nby_bus='" + by_bus + '\'' +
                ", \nby_airplane='" + by_airplane + '\'' +
                ", \nby_train='" + by_train + '\'' +
                ", \nby_electricity='" + by_electricity + '\'' +
                ", \nby_heating='" + by_heating + '\'' +
                ", \nby_diet='" + by_diet + '\'' +
                ", \nby_manufacture='" + by_manufacture + '\'' +
                ", \nincome='" + income + '\''
                ;
    }

    public String toCountry() {
        return "em_pcy='" + c_em_pcy + '\'' +
                ", \np_c_em='" + c_p_c_em + '\'' +
                ", \nle='" + c_le + '\'' +
                ", \nlifetime='" + c_lifetime + '\'' +
                ", \nby_electricity='" + c_by_electricity + '\'' +
                ", \nby_heating='" + c_by_heating + '\'' +
                ", \nby_diet='" + c_by_diet + '\'' +
                ", \nby_manufacture='" + c_by_manufacture + '\'' +
                ", \nincome='" + c_income + '\'';
    }

    public String toWorld() {
        return "em_pcy='" + w_em_pcy + '\'' +
                ", \np_c_em='" + w_p_c_em + '\'' +
                ", \nle='" + w_le + '\'' +
                ", \nlifetime='" + w_lifetime + '\'' +
                ", \nby_electricity='" + w_by_electricity + '\'' +
                ", \nby_heating='" + w_by_heating + '\'' +
                ", \nby_diet='" + w_by_diet + '\'' +
                ", \nby_manufacture='" + w_by_manufacture + '\'' +
                ", \nincome='" + w_income + '\'';
    }

    public String etical() {
        return "em_pcy='" + e_em_pcy + '\'' +
                ", \np_c_em='" + e_p_c_em + '\'' +
                ", \nle='" + e_le + '\'' +
                ", \nlifetime='" + e_lifetime + '\'' +
                ", \nby_electricity='" + e_by_electricity + '\'' +
                ", \nby_heating='" + e_by_heating + '\'' +
                ", \nby_diet='" + e_by_diet + '\'' +
                ", \nby_manufacture='" + e_by_manufacture + '\'' +
                ", \nincome='" + e_income + '\'';
    }


    public double K8() {
        return by_bus + by_car + by_train + by_motobike + by_airplane;
    }

    public double K15() {
        return by_electricity + by_heating;
    }

    public double K18() {
        return by_diet;
    }

    public double K19() {
        return by_manufacture;
    }

    public double porcientoEtico(double value) {
        return (e_em_pcy / value * 100) - 100;
    }

    public double P4() {
        return Constant.LDL_excess_carbon_em * (em_pcy - e_em_pcy);
    }

    public double P5() {
        return Constant.LDL_excess_carbon_em * (p_c_em - e_p_c_em);
    }

    public double P6() {
        return Constant.LDL_excess_carbon_em * (le - e_le);
    }

    public double P7() {
        return P5() + P6();
    }

    public double P8() {
        return P4() * (K8() / em_pcy);
    }

    public double P10() {
        return P4() * (by_car / em_pcy);
    }

    public double P11() {
        return P4() * (by_motobike / em_pcy);
    }

    public double P12() {
        return P4() * (by_bus / em_pcy);
    }

    public double P13() {
        return P4() * (by_airplane / em_pcy);
    }

    public double P14() {
        return P4() * (by_train / em_pcy);
    }

    public double P16() {
        return P4() * (by_electricity / em_pcy);
    }

    public double P17() {
        return P4() * (by_heating / em_pcy);
    }

    public double P18() {
        return P4() * (by_diet / em_pcy);
    }

    public double P19() {
        return P4() * (by_manufacture / em_pcy);
    }

    //Life years lost
    public double LYL(double Px) {
        return Px / 365;
    }

    //trees required per year to compensate excess carbon emmissions
    public double toCompensate(double personal, double etica) {
        return ((personal - etica) * 1000) / (Constant.CARBON_ABSORTION_POUNDS * Constant.CARBON_ABSORTION_FACTOR);
    }

    //Hectares planted (2000 trees/Ha)
    public double hePlanted(double personal, double etica) {
        return toCompensate(personal, etica) / 2000;
    }

    //Life days lost due to excess  accumulation till date
    public double tillDate(double personal,double etica,int E6,int E4) {
        return ((income-e_income)/Constant.excess_GDP)*Constant.excess_meeting_deficit*Constant.excess_deficit_zone*(E6-E4-18)*365;
    }


}
