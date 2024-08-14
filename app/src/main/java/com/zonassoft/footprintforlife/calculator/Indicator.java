package com.zonassoft.footprintforlife.calculator;

import android.content.Context;

import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.room.table.IndicatorEntity;


public class Indicator {
    private String indicator_code;
    private Context ctx;

    public Indicator(String indicator_code, Context context) {
        this.indicator_code = indicator_code;
        this.ctx = context;
    }

    public Indicator() {
    }

    public Indicator(Context ctx) {
        this.ctx = ctx;
    }

    protected double value(int year, String country) {
        DAO dao = AppDatabase.getDb(ctx).getDAO();
        double value = 0;

        IndicatorEntity ie = dao.getIndicator(indicator_code, country, year);
        if (ie != null)
            value = ie.getValue();

        return value;
    }
    public double toVal(int year, String country){
        return value(year,country);
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
