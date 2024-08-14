package com.zonassoft.footprintforlife.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.calculator.Generator;
import com.zonassoft.footprintforlife.data.ThisApp;
import com.zonassoft.footprintforlife.model.HProfileModel;
import com.zonassoft.footprintforlife.model.HResults;
import com.zonassoft.footprintforlife.model.HUserModel;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.utils.Tools;

import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;


public class FragmentMain extends Fragment {


    private PieChart chart;
    private BarChart chart_a, chart_b, chart_c, chart_d, chart_e, chart_f, chart_g;
    protected Typeface tfLight;
    protected DAO dao;
    protected HUserModel user;
    private View parent_view;

    private TextView text_meta, text_mundo, text_pais;

    private String[] h_data = new String[]{
            "Personal", "Pais", "Mundo", "Etica"
    };

    public FragmentMain() {
    }

    public static FragmentMain newInstance() {
        FragmentMain fragment = new FragmentMain();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_h_main, container, false);


        h_data = new String[]{
                getString(R.string.chart_personal), getString(R.string.chart_country), getString(R.string.chart_world), getString(R.string.chart_etical)
        };

        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "futura_medium_bt.ttf");
        dao = AppDatabase.getDb(getContext()).getDAO();
        user = ThisApp.get().getUser();
        parent_view = root.findViewById(R.id.parent_view);

        chart = root.findViewById(R.id.chart1);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(10, 0, 40, 40);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.TRANSPARENT);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(95f);
        chart.setTransparentCircleRadius(95f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(false);

        //chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setTypeface(tfLight);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(30f);


        // entry label styling
        chart.setEntryLabelColor(Color.TRANSPARENT);
        chart.setEntryLabelTypeface(tfLight);
        chart.setEntryLabelTextSize(0f);
        text_meta = root.findViewById(R.id.text_meta);
        text_mundo = root.findViewById(R.id.text_mundo);
        text_pais = root.findViewById(R.id.text_pais);

        //init others charts
        chart_a = root.findViewById(R.id.bar_a);
        chart_b = root.findViewById(R.id.bar_b);
        chart_c = root.findViewById(R.id.bar_c);
        chart_d = root.findViewById(R.id.bar_d);
        chart_e = root.findViewById(R.id.bar_e);
        chart_f = root.findViewById(R.id.bar_f);
        chart_g = root.findViewById(R.id.bar_g);

        initChart(chart_a, h_data);
        initChart(chart_b, h_data);
        initChart(chart_c, h_data);
        initChart(chart_d, new String[]{getString(R.string.chart_days_lost)});
        initChart(chart_e, new String[]{getString(R.string.chart_days_lost)});
        initChart(chart_f, h_data);
        initChart(chart_g, new String[]{getString(R.string.chart_life_days)});

        if (user != null) {




            if (dao.getProfile(1) != null) {
                Generator g = new Generator(getContext());
                HProfileModel info=dao.getProfile(1).original();
                info.country=user.country;
                info.id_country=user.id_country;

                displayDataResult(g.calculateFootprint(info));
            }
        }

        ((View) root.findViewById(R.id.share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });


        return root;
    }

    private void share() {
        Generator g = new Generator(getContext());
        HResults h = g.calculateFootprint(dao.getProfile(1).original());
        Tools.methodShare(getActivity(), h.em_pcy);
    }

    private void initChart(BarChart chart, String[] dataLeyent) {

        //chart.setOnChartValueSelectedListener(this);

        chart.getDescription().setEnabled(false);



        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);


        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);

        chart.setDrawValueAboveBar(false);
        chart.setHighlightFullBarEnabled(false);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                BarEntry entry = (BarEntry) e;


                if (entry.getYVals() != null) {
                    if (entry.getYVals().length > 1) {
                        Toast.makeText(getActivity(), getString(R.string.chart_desc_val) +" "+ entry.getYVals()[h.getStackIndex()], Toast.LENGTH_SHORT).show();
                        //Snackbar.make(parent_view, getString(R.string.chart_desc_val) +" "+ entry.getYVals()[h.getStackIndex()], Snackbar.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.chart_desc_val) +" "+ entry.getY(), Toast.LENGTH_SHORT).show();
                        //Snackbar.make(parent_view, getString(R.string.chart_desc_val) +" "+ entry.getY(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });


        // change the position of the y-labels
        YAxis leftAxis = chart.getAxisLeft();
        // leftAxis.setValueFormatter(new MyValueFormatter("K"));
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dataLeyent[(int) value];
            }
        });

        Legend l = chart.getLegend();
        l.setTypeface(tfLight);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
    }

    private void setData(int count, double range[]) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        String[] parties = new String[]{
                getString(R.string.chart_parties_transport), getString(R.string.chart_parties_electricity), getString(R.string.chart_parties_diet), getString(R.string.chart_parties_heating), getString(R.string.chart_parties_other)
        };

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) range[i % parties.length],
                    parties[i % parties.length],
                    getResources().getDrawable(R.drawable.ic_star)));
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
        //1
        colors.add(rgb("#FDD34D"));
        //2
        colors.add(rgb("#F4A77F"));
        //3
        colors.add(rgb("#969696"));
        //4
        colors.add(rgb("#000000"));


        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.TRANSPARENT);
        // data.setValueTypeface(tfLight);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();

    }

    private void setData1(int count, double[] rangeA, BarChart barChart, String val) {

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val1 = (float) rangeA[i];
            if (val1 < 0)
                val1 = 0;

            values.add(new BarEntry(
                    i,
                    new float[]{val1},
                    getResources().getDrawable(R.drawable.star)));
        }

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        //azul
        colors.add(rgb("#7FD3F7"));


        BarDataSet set1;


        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, val);
            set1.setDrawIcons(false);
            set1.setColors(colors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new StackedValueFormatter(false, "", 3));
            data.setValueTextColor(Color.TRANSPARENT);

            barChart.setData(data);
        }

        barChart.setFitBars(true);
        barChart.invalidate();

    }

    private void setData2(int count, double[] rangeA, double rangeB[], BarChart barChart, String[] dataLeyent) {

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val1 = (float) rangeA[i];
            if (val1 < 0)
                val1 = 0;
            float val2 = (float) rangeB[i];
            if (val2 < 0)
                val2 = 0;

            values.add(new BarEntry(
                    i,
                    new float[]{val1, val2},
                    getResources().getDrawable(R.drawable.star)));
        }

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        //azul
        colors.add(rgb("#7FD3F7"));
        //amarillo
        colors.add(rgb("#FDD34D"));


        BarDataSet set1;


        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "");
            set1.setDrawIcons(false);
            set1.setColors(colors);
            set1.setStackLabels(dataLeyent);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new StackedValueFormatter(false, "", 3));
            data.setValueTextColor(Color.TRANSPARENT);

            barChart.setData(data);
        }

        barChart.setFitBars(true);
        barChart.invalidate();

    }

    private void setData3(int count, double[] rangeA, double rangeB[], double rangeC[], double rangeD[], double rangeE[], BarChart barChart) {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val1 = (float) rangeA[i];
            if (val1 < 0)
                val1 = 0;
            float val2 = (float) rangeB[i];
            if (val2 < 0)
                val2 = 0;
            float val3 = (float) rangeC[i];
            if (val3 < 0)
                val3 = 0;
            float val4 = (float) rangeD[i];
            if (val4 < 0)
                val4 = 0;
            float val5 = (float) rangeE[i];
            if (val5 < 0)
                val5 = 0;

            values.add(new BarEntry(
                    i,
                    new float[]{val1, val2, val3, val4, val5},
                    getResources().getDrawable(R.drawable.star)));
        }

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        //1
        colors.add(rgb("#7FD3F7"));
        //2
        colors.add(rgb("#FDD34D"));
        //3
        colors.add(rgb("#F4A77F"));
        //4
        colors.add(rgb("#969696"));
        //5
        colors.add(rgb("#000000"));


        BarDataSet set1;


        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "");
            set1.setDrawIcons(false);
            set1.setColors(colors);
            set1.setStackLabels(new String[]{getString(R.string.chart_parties_transport), getString(R.string.chart_parties_electricity), getString(R.string.chart_parties_diet), getString(R.string.chart_parties_heating), getString(R.string.chart_parties_other)});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new StackedValueFormatter(false, "", 3));
            data.setValueTextColor(Color.TRANSPARENT);

            barChart.setData(data);
        }

        barChart.setFitBars(true);
        barChart.invalidate();

    }

    private SpannableString generateCenterSpannableText(String result) {

        SpannableString s = new SpannableString(result + "/\nTmCO₂e");
        int c1 = 6, c2 = 39;
        s.setSpan(new RelativeSizeSpan(2.0f), 0, s.length() - c1, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), s.length() - c1, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.DKGRAY), s.length() - c1, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(1.1f), s.length() - c1, s.length(), 0);
        return s;
    }

    private void displayDataResult(HResults pro) {

        boolean success = pro.em_pcy > 0;
        if (success) {


            chart.setCenterTextTypeface(tfLight);
            chart.setCenterText(generateCenterSpannableText(Tools.decimalFormat3(pro.em_pcy) + ""));
            double[] datos = new double[]{pro.K8(), pro.by_electricity, pro.by_diet, pro.by_heating, pro.by_manufacture};
            setData(5, datos);
            text_meta.setText(Tools.decimalFormat(1.73) + " Tm CO₂");
            text_mundo.setText(Tools.decimalFormat(pro.w_em_pcy) + " Tm CO₂");
            text_pais.setText(Tools.decimalFormat(pro.c_em_pcy) + " Tm CO₂");

            //
            double[] rangeA = new double[]{pro.em_pcy, pro.c_em_pcy, pro.w_em_pcy, pro.e_em_pcy};
            //
            double[] rangeA1 = new double[]{pro.p_c_em, pro.c_p_c_em, pro.w_p_c_em, pro.e_p_c_em};
            double[] rangeB1 = new double[]{pro.le, pro.c_le, pro.w_le, pro.e_le};
            //
            double[] rangeA2 = new double[]{pro.K8(), pro.c_by_transport, pro.w_by_transport, pro.e_by_transport};
            double[] rangeB2 = new double[]{pro.by_electricity, pro.c_by_electricity, pro.w_by_electricity, pro.e_by_electricity};
            double[] rangeC2 = new double[]{pro.by_diet, pro.c_by_diet, pro.w_by_diet, pro.e_by_diet};
            double[] rangeD2 = new double[]{pro.by_heating, pro.c_by_heating, pro.w_by_heating, pro.e_by_heating};
            double[] rangeE2 = new double[]{pro.by_manufacture, pro.c_by_manufacture, pro.w_by_manufacture, pro.e_by_manufacture};
            //
            double[] rangeA3 = new double[]{pro.P4()};
            //
            double[] rangeA4 = new double[]{pro.P5()};
            double[] rangeB4 = new double[]{pro.P6()};
            //
            double[] rangeA5 = new double[]{pro.income, pro.c_income, pro.w_income, pro.e_income};
            //
            double[] rangeA6 = new double[]{pro.total_lyt};
            double[] rangeB6 = new double[]{pro.future_lyt};

            setData1(4, rangeA, chart_a, getString(R.string.chart_parties_emissions));
            setData2(4, rangeA1, rangeB1, chart_b, new String[]{getString(R.string.chart_parties_accumulated),getString(R.string.chart_parties_future)});
            setData3(4, rangeA2, rangeB2, rangeC2, rangeD2, rangeE2, chart_c);
            setData1(1, rangeA3, chart_d, getString(R.string.chart_parties_days_lost));
            setData2(1, rangeA4, rangeB4, chart_e, new String[]{getString(R.string.chart_parties_accumulated),getString(R.string.chart_parties_future)});
            setData1(4, rangeA5, chart_f, getString(R.string.chart_parties_income));
            setData2(1, rangeA6, rangeB6, chart_g, new String[]{getString(R.string.chart_parties_accumulated),getString(R.string.chart_parties_future)});



        }

    }


}