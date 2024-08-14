package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorCo2EByRoadTravels extends Indicator {

    public IndicatorCo2EByRoadTravels(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.IROAD_GASOLINE_FUEL_CONSUMATION_PC,getCtx());
        Indicator indB = new Indicator(Constant.IROAD_DIESEL_FUEL_CONSUMATION_PC,getCtx());
        Indicator indC = new Indicator(Constant.ICO2_INTENSITY,getCtx());
        return (indA.value(year, code) + indB.value(year, code)) * indC.value(year, code);
    }
}
