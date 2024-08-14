package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

import com.zonassoft.footprintforlife.data.Constant;


public class IndicatorAirTravelsPCY extends Indicator {

    public IndicatorAirTravelsPCY(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.IAIR_TRANSPORT_PASSANGERS_CARRIED,getCtx());
        Indicator indB = new Indicator(Constant.IPOPULATION,getCtx());
        return indA.value(year, code) / indB.value(year, code);
    }
}
