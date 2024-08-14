package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorCo2EByElectricity extends Indicator {

    public IndicatorCo2EByElectricity(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {

        Indicator indA = new Indicator(Constant.IELECTRICITY_POWER_CONSUMATION_PC,getCtx());
        Indicator indB = new Indicator(Constant.IRENEWABLE_ELECTRICITY_OUTPUT,getCtx());

        return indA.value(year, code) * 0.25 * (1 - (indB.value(year, code) / 100)) / 1000;
    }
}
