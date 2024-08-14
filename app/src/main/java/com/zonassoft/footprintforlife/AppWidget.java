package com.zonassoft.footprintforlife;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.RemoteViews;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.zonassoft.footprintforlife.calculator.Generator;
import com.zonassoft.footprintforlife.data.ThisApp;
import com.zonassoft.footprintforlife.model.HResults;
import com.zonassoft.footprintforlife.model.HUserModel;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.utils.Tools;

import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static PieChart chart;
    static HResults info;
    static Typeface tfLight;
    static DAO dao;
    static HUserModel user;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        tfLight = Typeface.createFromAsset(context.getAssets(), "futura_medium_bt.ttf");
        dao = AppDatabase.getDb(context).getDAO();
        user = ThisApp.get().getUser();
        info=new HResults();
        if (user != null) {
            if (dao.getProfile(1) != null) {
                Generator g = new Generator(context);
                info=g.calculateFootprint(dao.getProfile(1).original());
            }
        }
        double[] datos = new double[]{info.K8(), info.K15(), info.K18(), info.K19()};
        //
        chart=new PieChart(context);

        chart.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

        //
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(context.getResources().getColor(R.color.ch_semi_trans));

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(95f);
        chart.setTransparentCircleRadius(95f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(false);

        chart.setEntryLabelTextSize(0f);
        chart.setData(setData(4,datos));
        chart.highlightValues(null);
        chart.invalidate();

        Legend l = chart.getLegend();
        l.setCustom(new ArrayList<>());



        // entry label styling
        chart.setEntryLabelColor(Color.TRANSPARENT);
        //chart.setEntryLabelTypeface(tfRegular);

        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText(Tools.decimalFormat(info.em_pcy) + ""));

        //
        chart.measure(View.MeasureSpec.makeMeasureSpec(300,View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(300,View.MeasureSpec.EXACTLY));
        chart.layout(0, 0, chart.getMeasuredWidth(), chart.getMeasuredHeight());

        Bitmap chartBitmap = getBitmapFromView(chart);

        views.setImageViewBitmap(R.id.chart1, chartBitmap);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static PieData setData(int count, double range[]) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        String[] parties = new String[]{
                "TRANSPORTE", "ELECTRICIDAD/CLIMA", "DIETA", "MANOFACTURE"
        };

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) range[i % parties.length],
                    parties[i % parties.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        //azul
        colors.add(rgb("#7FD3F7"));
        //amarillo
        colors.add(rgb("#FDD34D"));
        //rosado
        colors.add(rgb("#F4A77F"));
        //no se
        colors.add(rgb("#969696"));

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.TRANSPARENT);
        // data.setValueTypeface(tfLight);
        return data;

    }

    private static SpannableString generateCenterSpannableText(String result) {

        SpannableString s = new SpannableString(result + "/\nKgCO₂e\nSu de huella\n de carbono");
        SpannableString se = new SpannableString("2e545223");
        int c1 = 32, c2 = 25;
        s.setSpan(new RelativeSizeSpan(1.5f), 0, s.length() - c1, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), s.length() - c1, s.length() - c2, 0);
        s.setSpan(new ForegroundColorSpan(Color.DKGRAY), s.length() - c1, s.length() - c2, 0);
        s.setSpan(new RelativeSizeSpan(.9f), s.length() - c1, s.length() - c2, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), s.length() - c2, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(.5f), s.length() - c2, s.length(), 0);
      /*  s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);*/
        return s;
    }

    private static Bitmap makeTransparentBitmap(Bitmap bmp, int alpha) {
        Bitmap transBmp = Bitmap.createBitmap(bmp.getWidth(),
                bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBmp);
        final Paint paint = new Paint();
        paint.setAlpha(alpha);
        canvas.drawBitmap(bmp, 0, 0, paint);
        return transBmp;
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
}

