package com.zonassoft.footprintforlife.calculator;

import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorIncomeAfterTaxesPC extends Indicator {

    public IndicatorIncomeAfterTaxesPC(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.IGDP_PC,getCtx());
        Indicator indB = new Indicator(Constant.ITAX_REVENUE,getCtx());
        return indA.value(year, code) * ((100 - indB.value(year, code)) / 100);
    }

    public double val(int year, String code) {
        return value(year, code);
    }
}
