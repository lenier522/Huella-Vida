package com.zonassoft.footprintforlife.calculator;


public class CalculatorTransport {
    private double emPerType;


    public CalculatorTransport(double emPerType) {
        this.emPerType = emPerType;
    }

    public double calculate(double kms_by_car_month) {
        double value = (kms_by_car_month * emPerType) / 1000000;
        return value;
    }

    public double calculate(double kms_by_car_month, int persons) {
        double value = 0;
        if (persons != 0)
            value = (kms_by_car_month * 12 * emPerType) / (persons * 1000000);

        return value;
    }
}
