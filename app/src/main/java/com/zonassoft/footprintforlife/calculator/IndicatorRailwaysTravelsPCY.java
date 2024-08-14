package com.zonassoft.footprintforlife.calculator;

import com.zonassoft.footprintforlife.data.Constant;

public class IndicatorRailwaysTravelsPCY extends Indicator {

    public IndicatorRailwaysTravelsPCY() {
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new Indicator(Constant.IRAILWAYS_PASSANGERS_CARRIED,getCtx());
        Indicator indB = new Indicator(Constant.IPOPULATION,getCtx());
        return indA.value(year, code)*1000 / indB.value(year, code);
    }
}
