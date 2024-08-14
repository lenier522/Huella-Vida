package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;

public class IndicatorCo2EFromElectricityHP extends Indicator {

    public IndicatorCo2EFromElectricityHP(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.ICO2_EMISSIONS_FROM_ELECTRICITY, getCtx());
        Indicator indB = new Indicator(Constant.ICO2_EMISSIONS_PC, getCtx());
        if (indB.value(year, code) != 0)
            return (indA.value(year, code) / 100) * indB.value(year, code);
        return 0;
    }
}
