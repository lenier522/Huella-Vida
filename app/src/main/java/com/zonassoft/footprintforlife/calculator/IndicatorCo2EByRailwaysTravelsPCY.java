package com.zonassoft.footprintforlife.calculator;

import android.content.Context;

public class IndicatorCo2EByRailwaysTravelsPCY extends Indicator {

    public IndicatorCo2EByRailwaysTravelsPCY(Context ctx) {
        super(ctx);
    }

    @Override
    protected double value(int year, String code) {
        Indicator indA = new IndicatorRailwaysTravelsPCY();
        double indB = Generator.co2EmPerTypeOfTransport("train");
        return (indA.value(year, code) * indB * 1000) / 1000000;
    }
}
