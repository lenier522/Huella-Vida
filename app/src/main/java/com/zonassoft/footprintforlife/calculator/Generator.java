package com.zonassoft.footprintforlife.calculator;

import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;
import com.zonassoft.footprintforlife.model.HProfileModel;
import com.zonassoft.footprintforlife.model.HResults;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.utils.Tools;


public class Generator {

    private DAO dao;
    private Context ctx;
    private double[] etical;
    private double[] fx_annual;
    private double[] etical2c;
    private double carbon_absortion;
    private double[] factores;

    public Generator(Context ctx) {
        this.carbon_absortion = Constant.CARBON_ABSORTION_FACTOR * Constant.CARBON_ABSORTION_POUNDS;
        this.ctx = ctx;
        this.dao = AppDatabase.getDb(ctx).getDAO();
        etical = new double[]{2.071848601, 2.044642197, 2.007873217, 1.966474283, 1.913909458, 1.852353868, 1.780254547, 1.703849977, 1.612471623, 1.513902756, 1.408074205, 1.282532307, 1.125665395, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128, 0.969272128};
        etical2c = new double[]{3.086619526, 3.119747394, 3.155174486, 3.1987685, 3.248350811, 3.304983234, 3.369470878, 3.442901557, 3.526961163, 3.623103088, 3.732369803, 3.856363206, 3.99760169, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495, 3.957400495};
        fx_annual = new double[]{0.7140, 0.7164, 0.7188, 0.7212, 0.7236, 0.7260, 0.7284, 0.7308, 0.7332, 0.7356, 0.7380, 0.7404, 0.7428, 0.7452, 0.7476, 0.7500, 0.7524, 0.7548, 0.7572, 0.7596, 0.7620, 0.7644, 0.7668, 0.7692, 0.7716, 0.7740, 0.7764, 0.7788, 0.7812, 0.7836, 0.7860, 0.7884, 0.7908, 0.7932, 0.7956, 0.7980, 0.8004, 0.8028, 0.8052, 0.8076, 0.8100, 0.8124, 0.8160, 0.8200, 0.8238, 0.8274, 0.8306, 0.8338, 0.8366, 0.8395, 0.8420, 0.8440, 0.8454, 0.8466, 0.8474, 0.8477, 0.8484, 0.8495, 0.8499, 0.8501, 0.8503, 0.8498, 0.8499, 0.8511, 0.8530, 0.8555, 0.8577, 0.8598, 0.8616, 0.8635, 0.8649, 0.8664, 0.8683, 0.8705, 0.8737, 0.8777, 0.8820, 0.8864, 0.8910, 0.8959, 0.9016, 0.9087, 0.9160, 0.9237, 0.9323, 0.9398, 0.9461, 0.9517, 0.9563, 0.9612, 0.9652, 0.9726, 0.9773, 0.9791, 0.9804, 0.9828, 0.9878, 0.9909, 0.9939};
        factores = new double[]{0.25, 0.5, 1, 1.25, 1.5, 0};
    }

    public static double co2EmPerTypeOfTransport(String type) {
        double rest = 0.00;
        switch (type) {
            case "train":
                rest = 25.00;
                break;
            case "small_car":
                rest = 190.00;
                break;
            case "big_car":
                rest = 0.00;
                break;
            case "bus":
                rest = 62.00;
                break;
            case "motobike":
                rest = 120.00;
                break;
            case "plane":
                rest = 45.00;
                break;
            case "ship":
                rest = 0.00;
                break;
            default:
                break;

        }
        return rest;
    }

    public double adjustmentTypesOfDiet(int type) {


        if (type==0) {
            return (double) 1;
        } else if (type==1) {
            return (double) 1.32;
        } else if (type==2) {
            return (double) 0.76;
        } else if (type==3) {
            return (double) 0.68;
        } else if (type==4) {
            return (double) 0.6;
        }

        return 0;
    }

    public HResults calculateFootprint(HProfileModel p) {

        HResults cal = new HResults();
        int year = 2018;
        String country= Tools.getCountry(ctx,p.id_country);

        int current_ethical = ((2018 - 1950) / 5) + 1;
        int personal_ethical = ((p.year - 1950) / 5) + 1;
        double fx_factor = 0.9939;
        if (p.year <= 2017) {
            if (p.year > 1918)
                fx_factor = fx_annual[(p.year - 1919)];
            else
                fx_factor = 0.7140;
        }

        try {

            IndicatorIncomeAfterTaxesPC icpat = new IndicatorIncomeAfterTaxesPC(ctx);
            IndicatorImportsPerGDPPC iigpdpc = new IndicatorImportsPerGDPPC(ctx);
            IndicatorMethaneEPC impc = new IndicatorMethaneEPC(ctx);
            IndicatorCo2EByTransport iebt = new IndicatorCo2EByTransport(ctx);
            IndicatorCo2EByElectricity iebe = new IndicatorCo2EByElectricity(ctx);
            IndicatorCo2EFromElectricityHP iebehp = new IndicatorCo2EFromElectricityHP(ctx);
            IndicatorCo2EByHeating iebh = new IndicatorCo2EByHeating(ctx);
            IndicatorCo2EByManofacturing iebm = new IndicatorCo2EByManofacturing(ctx);
            IndicatorCo2EByGDP iebgpd = new IndicatorCo2EByGDP(ctx);
            Indicator igpdc = new Indicator(Constant.IGDP_PC, ctx);
            Indicator icepc = new Indicator(Constant.ICO2_EMISSIONS_PC, ctx);
            Indicator ile = new Indicator(Constant.ILIFE_EXPECTANCY_AT_BIRTH, ctx);


            double L4 = icepc.value(year, "World");
            cal.w_em_pcy = L4;
            double L5 = dao.cumulativeEmisions(p.year, Constant.ICO2_EMISSIONS_PC, "World");
            cal.w_p_c_em = L5;
            //ver
            double L6 = (ile.value(year, country) - (year - p.year)) * L4;
            cal.w_le = L6;
            double L7 = L5 + L6;
            cal.w_lifetime = L7;

            double L15 = iebehp.value(year, "World");
            //cal.w_em_pcy=L15;
            double L16 = iebe.value(year, "World");
            cal.w_by_electricity = L16;
            double L17 = iebh.value(year, "World");
            cal.w_by_heating = L17;
            double L18 = impc.value(year, "World");
            cal.w_by_diet = L18;
            double L19 = iebm.value(year, "World");
            cal.w_by_manufacture = L19;
            double L21 = igpdc.value(year, "World");
            cal.w_income = L21;

            double K4 = icepc.value(year, country);
            cal.c_em_pcy = K4;
            //(M4*(E6-E4))*SUMIF('conv fx annual vs lft CO2'!A2:A100,"="&E4,'conv fx annual vs lft CO2'!C2:C100)
            double K5 = (K4 * (year - p.year)) * fx_factor;
            cal.c_p_c_em = K5;
            double K6 = (ile.value(year, country) - (year - p.year)) * K4;
            cal.c_le = K6;
            double K7 = K5 + K6;
            cal.c_lifetime = K7;
            double K8 = iebt.value(year, country);
            cal.c_by_transport = K8;
            double K15 = iebehp.value(year, country);
            //cal.c_em_pcy=L4;
            double K16 = iebe.value(year, country);
            cal.c_by_electricity = K16;
            double K17 = iebh.value(year, country);
            cal.c_by_heating = K17;
            double K18 = impc.value(year, country);
            cal.c_by_diet = K18;
            double K19 = iebm.value(year, country);
            cal.c_by_manufacture = K19;
            double K21 = igpdc.value(year, country);
            cal.c_income = K21;
            double L8 = K8 / iebt.value(year, "World");
            cal.w_by_transport = L8;

            //emissions pcy (epc)
            double epc = 0;
            //Trasnport emission by car (bc)
            double bc = new CalculatorTransport(co2EmPerTypeOfTransport("small_car")).calculate(p.t_kms_by_car, p.t_persons_cars);
            cal.by_car = bc;
            epc += bc;
            //Trasnport emission by Motobike (bm)
            double bm = new CalculatorTransport(co2EmPerTypeOfTransport("motobike")).calculate(p.t_kms_by_motobike) * 12;
            cal.by_motobike = bm;
            epc += bm;
            //Trasnport emission by Bus (bb)
            double bb = new CalculatorTransport(co2EmPerTypeOfTransport("bus")).calculate(p.t_kms_by_bus);
            cal.by_bus = bb;
            epc += bb;
            //Trasnport emission by Train (bt)
            double bt = new CalculatorTransport(co2EmPerTypeOfTransport("train")).calculate(p.t_kms_by_train);
            cal.by_train = bt;
            epc += bt;
            //Trasnport emission by Plane (ba)
            double ba = new CalculatorTransport(co2EmPerTypeOfTransport("plane")).calculate(p.v_kms_by_airplane);
            cal.by_airplane = ba;
            epc += ba;

            double bec = factores[p.e_kwatts_aux] * K16;
            if (p.e_kwatts_aux >= 5)
                bec = ((((p.e_kwatts_custom - p.e_kwatts_r) * 12) / (p.h_number_peoples)) * (1 - (0 / 100)) * 0.25) / 1000;
            cal.by_electricity = bec;
            epc += bec;

            //by heating (bh)
            double bh = factores[p.h_fuel_gas_aux] * K17;
            if (p.h_fuel_gas_aux >= 5)
                bh = (((p.h_fuel_gas_custom + (p.h_number_peoples)) * 3) / (p.h_number_peoples)) / 1000;
            cal.by_heating = bh;
            epc += bh;
            //average income pc/month
            double aipm = p.g_individual / (p.g_dep);
            cal.income = aipm * 12;

            //Methane (carbon equivalent)  emissions by diet (cebd)
            //SUMIF('adjustment types of diet'!B9:B13,"="&E20,'adjustment types of diet'!C9:C13)*M18*((E21/100)/SUMIF('% GDP pc related to trade'!A5:A268,"="&D3,'% GDP pc related to trade'!BL5:BL268))
            double aux_iigpdpc = iigpdpc.value(year, country);
            double cebd = 0;
            if (aux_iigpdpc != 0)
                cebd = adjustmentTypesOfDiet(p.d_type) * impc.value(year, country) * (((100 - p.d_local) / 100) / iigpdpc.val(year, country));
            epc += cebd;
            cal.by_diet = cebd;

            //Carbon emissions by manufactures and state (cebme)
            double aux_icpat = icpat.value(year, country);
            double cebme = 0;
            if (aux_icpat != 0)
               cebme = (((iebm.value(year, country)) * (aipm * 12)) / icpat.value(year, country));

            epc += cebme;


            cal.by_manufacture = cebme;

            cal.em_pcy = epc;

            //pior cumulative emision
            double pce = K5 * (epc / K4) * fx_factor;
            cal.p_c_em = pce;

            double le = (ile.value(year, country) - (year - p.year)) * epc;
            cal.le = le;

            double lifetime = pce + le;
            cal.lifetime = lifetime;
            //etical
            double M4 = etical[current_ethical];
            cal.e_em_pcy = M4;
            double M5 = cumulatedEthical(personal_ethical, current_ethical);
            cal.e_p_c_em = M5;
            double M6 = (ile.value(year, country) - (year - p.year)) * M4;
            cal.e_le = M6;
            double M7 = M5 + M6;
            cal.e_lifetime = M7;
            cal.e_by_transport = M4 * ((bt + ba + bb + bc) / K4);
            cal.e_by_electricity = M4 * (bec / K4);
            cal.e_by_heating = M4 * (bh / K4);
            cal.e_by_diet = M4 * (cebd / K4);
            cal.e_by_manufacture = M4 * (cebme / K4);
            cal.e_income = 16368.0944;

            double P21 = cal.income - cal.e_income;
            cal.total_lyt = (P21 / Constant.excess_GDP) * Constant.excess_meeting_deficit * Constant.excess_deficit_zone * (year - p.year - 18) * 365;
            double future_lyt = (P21 / Constant.excess_GDP) * Constant.excess_meeting_deficit * Constant.excess_deficit_zone * ((ile.value(year, country) - 18)) * 365;
            cal.future_lyt=future_lyt-cal.total_lyt;



        } catch (Exception ex) {

        }

        return cal;
    }


    private double cumulatedEthical(int pos, int last) {
        double sum = 0;
        for (int i = pos; i <= last; i++)
            sum += etical2c[i];

        return sum;
    }

}
