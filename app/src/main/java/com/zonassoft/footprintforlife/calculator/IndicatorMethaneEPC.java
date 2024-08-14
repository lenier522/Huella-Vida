package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorMethaneEPC extends Indicator {

    public IndicatorMethaneEPC(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.IMETHANE_EMISSIONS, getCtx());
        Indicator indB = new Indicator(Constant.IPOPULATION, getCtx());
        if (indB.value(year, code) != 0)
            return indA.value(year, code) / indB.value(year, code) * 1000;
        return 0;
    }

}
