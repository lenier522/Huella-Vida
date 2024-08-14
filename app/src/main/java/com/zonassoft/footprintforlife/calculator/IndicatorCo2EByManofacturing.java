package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorCo2EByManofacturing extends Indicator {

    public IndicatorCo2EByManofacturing(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.ICO2_EMISSIONS_BY_MANOFACTURIN, getCtx());
        Indicator indB = new Indicator(Constant.ICO2_EMISSIONS_PC, getCtx());
        if (indB.value(year, code) != 0)
            //('co em by manufacturing'!BG53/100)*'co emissions pc'!BG53
            return (indA.value(year, code) / 100) * indB.value(year, code);
        return 0;
    }
}
