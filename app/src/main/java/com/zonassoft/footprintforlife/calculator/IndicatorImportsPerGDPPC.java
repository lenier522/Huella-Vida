package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorImportsPerGDPPC extends Indicator {

    public IndicatorImportsPerGDPPC(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.IGDP_PC, getCtx());
        Indicator indB = new IndicatorImportGoodServicesPC(getCtx());
        if (indB.value(year, code) != 0)
            return indB.value(year, code) / indA.value(year, code);
        return 0;
    }

    public double val(int year, String code) {
        return value(year, code);
    }

}
