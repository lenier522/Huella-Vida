package com.zonassoft.footprintforlife.calculator;

import android.content.Context;

public class IndicatorCo2EByHeating extends Indicator {

    public IndicatorCo2EByHeating(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new IndicatorCo2EFromElectricityHP(getCtx());
        Indicator indB = new IndicatorCo2EByElectricity(getCtx());
        return indA.value(year, code) - indB.value(year, code);
    }
}
