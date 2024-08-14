package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorExportGoodServicesPC extends Indicator {

    public IndicatorExportGoodServicesPC(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.IEXPORT_GOOD_SERVICES,getCtx());
        Indicator indB = new Indicator(Constant.IPOPULATION,getCtx());
        return indA.value(year, code) / indB.value(year, code);
    }
}
