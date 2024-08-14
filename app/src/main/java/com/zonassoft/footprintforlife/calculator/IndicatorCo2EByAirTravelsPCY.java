package com.zonassoft.footprintforlife.calculator;


import android.content.Context;

public class IndicatorCo2EByAirTravelsPCY extends Indicator {

    public IndicatorCo2EByAirTravelsPCY(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new IndicatorAirTravelsPCY(getCtx());
        double indB = Generator.co2EmPerTypeOfTransport("plane");
        return indA.value(year, code) * 3000 * indB / 1000000;
    }
}
