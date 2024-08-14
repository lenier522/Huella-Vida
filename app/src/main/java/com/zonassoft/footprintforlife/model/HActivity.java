package com.zonassoft.footprintforlife.model;

import android.graphics.drawable.Drawable;

/**
 * Class to save all values of activity.
 */

public class HActivity {

    public int image;
    public int color;
    public Drawable imageDrw;
    public String title;
    public String subtitle;
    public double kg;
    public boolean section = false;

    public HActivity() {
    }

    public HActivity(String title,String subtitle,boolean section) {
        this.title = title;
        this.subtitle = subtitle;
        this.section = section;
    }

    public HActivity(String title, String subtitle, double kg) {
        this.title = title;
        this.subtitle = subtitle;
        this.kg = kg;
    }
}
