package com.zonassoft.footprintforlife.data;

import android.content.Context;
import android.content.res.TypedArray;

import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.model.HActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@SuppressWarnings("ResourceType")
public class DataGenerator {

    private static Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }

    public static double[] getData(int max) {

        double[] data = new double[max];
        for (int i = 0; i < max; i++) {
            data[i] = randInt(300);
        }
        return data;

    }


    public static List<String> getStringsMonth(Context ctx) {
        List<String> items = new ArrayList<>();
        String arr[] = ctx.getResources().getStringArray(R.array.month);
        for (String s : arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }


    /**
     * Generate dummy data activities
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<HActivity> getActivitiesData(Context ctx, int mode) {
        List<HActivity> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.huella_activities_images);
        TypedArray color_arr = ctx.getResources().obtainTypedArray(R.array.huella_activities_colors);
        String name_arr[] = ctx.getResources().getStringArray(R.array.huella_activities_titles);
        String sub_name_arr[] = ctx.getResources().getStringArray(R.array.huella_activities_subtitles);
        int init=0,last=8;
        if(mode==1){
           init=8;
           last=10;
        }

        for (int i = init; i < last; i++) {
            HActivity obj = new HActivity();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.color = color_arr.getResourceId(i, -1);
            obj.title = name_arr[i];
            obj.subtitle = sub_name_arr[i];
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    public static String formatTime(long time) {
        // income time
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);

        // current time
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        SimpleDateFormat dateFormat = null;
        if (date.get(Calendar.YEAR) == curDate.get(Calendar.YEAR)) {
            if (date.get(Calendar.DAY_OF_YEAR) == curDate.get(Calendar.DAY_OF_YEAR)) {
                dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            } else {
                dateFormat = new SimpleDateFormat("MMM d", Locale.US);
            }
        } else {
            dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        }
        return dateFormat.format(time);
    }

    private static int getRandomIndex(int max) {
        return r.nextInt(max - 1);
    }
}
