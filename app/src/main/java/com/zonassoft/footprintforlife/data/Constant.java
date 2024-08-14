package com.zonassoft.footprintforlife.data;

public class Constant {


    //base api url
    public static String WEB_URL = "http://huella.zonassoft.com";

    //api secret
    public static String API_TOKEN = "C2F7C15AB8FCCC3395F9E7475A955";

    //global
    public static double CARBON_ABSORTION_POUNDS=2.52;
    public static double CARBON_ABSORTION_FACTOR=0.45359237;
    public static double CARBON_ABSORTION=CARBON_ABSORTION_POUNDS*CARBON_ABSORTION_FACTOR;

    //LDL due to excess co em
    public static double excess_deaths=218013069.4270000000000000;
    public static double excess_LYL=4064590919.1269200000000000;
    public static double total_excess_co2_emmissions_till_2050=338063585641.4390000000000000;
    public static double LYL_excess_carbon_em=0.03725821;
    public static double LDL_excess_carbon_em=13.59924692;

    //LYD to exces aco
    public static double  excess_GDP=28036727427396.8;
    public static double  excess_meeting_deficit=0.271357617;
    public static double  excess_deficit_zone=338695497.3;
    public static double  prop_deficit_zone=19327144.49;
    public static double  LYL=excess_GDP*excess_meeting_deficit*excess_deficit_zone;

    //indicators BASES
    public static String ICO2_EMISSIONS_PC="he_co2_emissions";
    public static String ICO2_INTENSITY="he_co2_intensity";
    public static String ICO2_EMISSIONS_FROM_TRANSPORT="he_co2_e_by_transport";
    public static String IPOPULATION="he_population";
    public static String IAIR_TRANSPORT_PASSANGERS_CARRIED="he_co2_air_travels_passangers";
    public static String IRAILWAYS_PASSANGERS_CARRIED="he_co2_train_travels_passangers";
    public static String IROAD_GASOLINE_FUEL_CONSUMATION_PC="he_fuel_gasoline_pc";
    public static String IROAD_DIESEL_FUEL_CONSUMATION_PC="he_fuel_diesel_pc";
    public static String ICO2_EMISSIONS_FROM_ELECTRICITY="he_co2_e_by_electricity";
    public static String IRENEWABLE_ELECTRICITY_OUTPUT="he_co2_e_by_renewbable";
    public static String IELECTRICITY_POWER_CONSUMATION_PC="he_co2_e_kwats_pc";
    public static String IMETHANE_EMISSIONS="he_co2_e_by_agricultura";
    public static String IGDP_PC="he_GDP_PC";
    public static String IIMPORT_GOOD_SERVICES="he_imports_goods";
    public static String IEXPORT_GOOD_SERVICES="he_exports_goods";
    public static String ICO2_EMISSIONS_BY_MANOFACTURIN="he_co2_by_manufacture";
    public static String ITAX_REVENUE="he_tax_renuew";
    public static String ILIFE_EXPECTANCY_AT_BIRTH="he_life_expentancy";



}
