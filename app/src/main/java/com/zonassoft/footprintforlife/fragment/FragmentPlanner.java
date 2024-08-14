package com.zonassoft.footprintforlife.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.calculator.Generator;
import com.zonassoft.footprintforlife.calculator.Indicator;
import com.zonassoft.footprintforlife.data.Constant;
import com.zonassoft.footprintforlife.data.ThisApp;
import com.zonassoft.footprintforlife.model.HProfileModel;
import com.zonassoft.footprintforlife.model.HResults;
import com.zonassoft.footprintforlife.model.HUserModel;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.utils.Tools;
import com.zonassoft.footprintforlife.utils.ViewAnimation;

import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;


public class FragmentPlanner extends Fragment {

    public static final int DIALOG_QUEST_CODE = 300;
    View plan_a, plan_b, plan_c;
    ProgressBar bar_a, bar_b, bar_c;
    TextView text_a, text_b, text_c;

    private ImageView bt_toggle_why;
    private View lyt_expand_why, lyt_why;

    private View parent_view;

    private BarChart chart_a, chart_c, chart_d;
    private CombinedChart chart_b;
    protected Typeface tfLight;
    protected DAO dao;
    protected HUserModel user;

    public FragmentPlanner() {
    }

    public static FragmentPlanner newInstance() {
        FragmentPlanner fragment = new FragmentPlanner();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_h_plan, container, false);

        parent_view = root.findViewById(R.id.parent_view);

        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "futura_medium_bt.ttf");
        dao = AppDatabase.getDb(getContext()).getDAO();
        user = ThisApp.get().getUser();
        bar_a = root.findViewById(R.id.progress_a);
        bar_b = root.findViewById(R.id.progress_b);
        bar_c = root.findViewById(R.id.progress_c);

        text_a = root.findViewById(R.id.text_a);
        text_b = root.findViewById(R.id.text_b);
        text_c = root.findViewById(R.id.text_c);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        ((View) root.findViewById(R.id.lyt_plan_1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFullscreen(2);
            }
        });
        ((View) root.findViewById(R.id.lyt_plan_b)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFullscreen(3);
            }
        });
        ((View) root.findViewById(R.id.lyt_plan_c)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFullscreen(4);
            }
        });

        //init others charts
        chart_a = root.findViewById(R.id.bar_a);
        chart_b = root.findViewById(R.id.bar_b);
        chart_c = root.findViewById(R.id.bar_c);
        chart_d = root.findViewById(R.id.bar_d);

        initChart(chart_a, new String[]{getString(R.string.current_footprint), "2021", "2025", "2030"}, 1);
        initCombinedChart(chart_b, new String[]{getString(R.string.chart_present), getString(R.string.chart_eco)}, 0);
        initChart(chart_c, new String[]{getString(R.string.chart_present), getString(R.string.chart_eco)}, 0);
        initChart(chart_d, new String[]{getString(R.string.chart_present), getString(R.string.chart_eco)}, 0);

        displayDataResult();

        // why
        bt_toggle_why = (ImageView) root.findViewById(R.id.bt_toggle_why);
        lyt_expand_why = (View) root.findViewById(R.id.lyt_expand_why);
        lyt_why = (View) root.findViewById(R.id.lyt_why);

        lyt_why.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(bt_toggle_why, lyt_expand_why);
            }
        });
        return root;
    }

    private void toggleSection(View view, View lyt) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    //Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(90);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    private void displayDataResult() {

        Indicator ile = new Indicator(Constant.ILIFE_EXPECTANCY_AT_BIRTH, getContext());

        Generator g = new Generator(getContext());
        HResults cal1, cal2, cal3, cal4;

        HProfileModel ri2 = dao.getProfile(2).original();
        HProfileModel ri3 = dao.getProfile(3).original();
        HProfileModel ri4 = dao.getProfile(4).original();

        cal2 = g.calculateFootprint(ri2);
        cal3=  g.calculateFootprint(ri3);
        cal4 = g.calculateFootprint(ri4);
        cal1 = g.calculateFootprint(dao.getProfile(1).original());

        bar_a.setProgress((int) (cal2.em_pcy / cal1.em_pcy * 100));
        bar_b.setProgress((int) (cal3.em_pcy / cal1.em_pcy * 100));
        bar_c.setProgress((int) (cal4.em_pcy / cal1.em_pcy * 100));

        text_a.setText(Tools.decimalFormat(cal2.em_pcy) + "\nTm CO₂");
        text_b.setText(Tools.decimalFormat(cal3.em_pcy) + "\nTm CO₂");
        text_c.setText(Tools.decimalFormat(cal4.em_pcy) + "\nTm CO₂");

        double[] rangeA = new double[]{cal1.by_car, cal2.by_car, cal3.by_car, cal4.by_car};
        double[] rangeB = new double[]{cal1.by_bus, cal2.by_bus, cal3.by_bus, cal4.by_bus};
        double[] rangeC = new double[]{cal1.by_motobike, cal2.by_motobike, cal3.by_motobike, cal4.by_motobike};
        double[] rangeD = new double[]{cal1.by_train, cal2.by_train, cal3.by_train, cal4.by_train};
        double[] rangeE = new double[]{cal1.by_airplane, cal2.by_airplane, cal3.by_airplane, cal4.by_airplane};
        double[] rangeF = new double[]{cal1.by_electricity, cal2.by_electricity, cal3.by_electricity, cal4.by_electricity};
        double[] rangeG = new double[]{cal1.by_diet, cal2.by_diet, cal3.by_diet, cal4.by_diet};
        double[] rangeH = new double[]{cal1.by_heating, cal2.by_heating, cal3.by_heating, cal4.by_heating};
        double[] rangeI = new double[]{cal1.by_manufacture, cal2.by_manufacture, cal3.by_manufacture, cal4.by_manufacture};


        double E43 = cal1.em_pcy;
        double E44 = E43 - cal1.e_em_pcy;
        double E45 = cal1.p_c_em;
        double E46 = cal1.le;
        double E47 = cal1.lifetime;
        double E48 = cal1.lifetime - cal1.e_lifetime;
        double E49 = E48 * Constant.LDL_excess_carbon_em / 365;
        double E50 = (E44 * 1000 / Constant.CARBON_ABSORTION) + ((cal1.P5() / Constant.LDL_excess_carbon_em * 1000) / Constant.CARBON_ABSORTION) / (ile.toVal(2018, Tools.getCountry(getContext(),user.id_country)) - (2018 - user.getYear()));
        double E51 = E50 / 2000;

        double F43 = ((cal2.em_pcy * (ri2.year - 2018)) + (cal3.em_pcy * (ri3.year - ri2.year)) + (cal4.em_pcy * ((ile.toVal(2018, Tools.getCountry(getContext(),user.id_country)) - (ri4.year - user.getYear())) - 4))) / (ile.toVal(2018, Tools.getCountry(getContext(),user.id_country)) - (2018 - user.getYear()));
        double F44 = F43 - cal1.e_em_pcy;
        double F45 = E45;
        double F46 = F43 * (ile.toVal(2018, Tools.getCountry(getContext(),user.id_country)) - (2021 - user.getYear()));
        double F47 = F45 + F46;
        double F48 = F47 - cal1.e_lifetime;
        double F49 = F48 * Constant.LDL_excess_carbon_em / 365;
        double F50 = (F44 * 1000 / Constant.CARBON_ABSORTION) + ((cal1.P5() / Constant.LDL_excess_carbon_em * 1000) / Constant.CARBON_ABSORTION) / (ile.toVal(2018, Tools.getCountry(getContext(),ri2.id_country)) - (2018 - user.getYear()));
        double F51 = F50 / 2000;

        double[] rangeA1 = new double[]{E45, F45};
        double[] rangeB1 = new double[]{E46, F46};

        double[] rangeA2 = new double[]{E49, F49};
        double[] rangeA3 = new double[]{E50, F50};


        setData3(4, rangeA, rangeB, rangeC, rangeD, rangeE, rangeF, rangeG, rangeH, rangeI, chart_a);

        CombinedData data = new CombinedData();
        data.setData(generateLineData(2));
        data.setData(generateBarData(2, rangeA1, rangeB1));

        setData1(2, rangeA2, chart_c, getString(R.string.chart_parties_days_lost));

        setData1(2, rangeA3, chart_d, getString(R.string.chart_parties_trees));
        chart_b.setData(data);
        chart_b.invalidate();


    }



    private LineData generateLineData(int count) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();


        entries.add(new Entry(0 + 0.5f, 75));
        entries.add(new Entry(1 + 0.5f, 75));

        LineDataSet set = new LineDataSet(entries, getString(R.string.results_tag_etica));
        set.setColor(Color.rgb(0, 0, 0));
        set.setLineWidth(1.5f);
        set.setCircleColor(Color.rgb(0, 0, 0));
        set.setCircleRadius(0f);
        set.setFillColor(Color.TRANSPARENT);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);
        d.setValueTextColor(Color.TRANSPARENT);


        return d;
    }

    private BarData generateBarData(int count, double[] rangeA, double rangeB[]) {


        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry(0 + 0.5f, new float[]{(float) rangeA[0], (float) rangeB[0]}));
        entries2.add(new BarEntry(1, new float[]{(float) rangeA[1], (float) rangeB[1]}));

        ArrayList<Integer> colors = new ArrayList<>();
        //1
        colors.add(rgb("#7FD3F7"));
        //2
        colors.add(rgb("#FDD34D"));

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColors(colors);
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        // set1.setAxisDependency(YAxis.AxisDependency.);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setColors(colors);
        set2.setStackLabels(new String[]{getString(R.string.chart_parties_accumulated), getString(R.string.chart_parties_future)});

        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        // d.groupBars(0, groupSpace, barSpace); // start at x = 0
        d.setValueTextColor(Color.TRANSPARENT);

        return d;
    }

    private void setData1(int count, double[] rangeA, BarChart barChart, String valChart) {
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
        //1
        colors.add(rgb("#7FD3F7"));

        BarDataSet set1;


        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, valChart);
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

    private void setData2(int count, double[] rangeA, double rangeB[], BarChart barChart) {
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
        //1
        colors.add(rgb("#7FD3F7"));
        //2
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
            set1.setStackLabels(new String[]{getString(R.string.chart_parties_accumulated), getString(R.string.chart_parties_future)});

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


    private void setData3(int count, double[] rangeA, double rangeB[], double rangeC[], double rangeD[], double rangeE[], double rangeF[], double rangeG[], double rangeH[], double rangeI[], BarChart barChart) {
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
            float val6 = (float) rangeF[i];
            if (val6 < 0)
                val6 = 0;
            float val7 = (float) rangeG[i];
            if (val7 < 0)
                val7 = 0;
            float val8 = (float) rangeH[i];
            if (val8 < 0)
                val8 = 0;
            float val9 = (float) rangeI[i];
            if (val9 < 0)
                val9 = 0;

            values.add(new BarEntry(
                    i,
                    new float[]{val1, val2, val3, val4, val5, val6, val7, val8, val9},
                    getResources().getDrawable(R.drawable.star)));
        }

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        //1
        colors.add(rgb("#7FD3F7"));
        //2
        colors.add(rgb("#80d8ff"));
        //3
        colors.add(rgb("#40c4ff"));
        //4
        colors.add(rgb("#00b0ff"));
        //5
        colors.add(rgb("#0091ea"));
        //6
        colors.add(rgb("#FDD34D"));
        //7
        colors.add(rgb("#F4A77F"));
        //8
        colors.add(rgb("#969696"));
        //9
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
            set1.setStackLabels(new String[]{getString(R.string.chart_parties_by_car), getString(R.string.chart_parties_by_bus), getString(R.string.chart_parties_by_motor), getString(R.string.chart_parties_by_train), getString(R.string.chart_parties_by_airplane), getString(R.string.chart_parties_by_electricity), getString(R.string.chart_parties_by_diet), getString(R.string.chart_parties_by_heating), getString(R.string.chart_parties_by_other)});

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


    private void showDialogFullscreen(int plan) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        DialogPlanFragment newFragment = new DialogPlanFragment();
        newFragment.setRequestCode(DIALOG_QUEST_CODE);
        newFragment.setPlan(plan);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        newFragment.setOnCallbackResult(new DialogPlanFragment.CallbackResult() {
            @Override
            public void sendResult(int requestCode, Object obj) {
                if (requestCode == DIALOG_QUEST_CODE) {
                    displayDataResult();
                }
            }
        });
    }

    private void initChart(BarChart chart, String[] h_data, int legent) {

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
                        Toast.makeText(getActivity(), getString(R.string.chart_desc_val) + " " + entry.getYVals()[h.getStackIndex()], Toast.LENGTH_SHORT).show();
                        //Snackbar.make(parent_view, getString(R.string.chart_desc_val) +" "+ entry.getYVals()[h.getStackIndex()], Snackbar.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.chart_desc_val) + " " + entry.getY(), Toast.LENGTH_SHORT).show();
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
        leftAxis.setTypeface(tfLight);
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
                return h_data[(int) value];
            }
        });

        Legend l = chart.getLegend();


        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setTypeface(tfLight);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        if (legent == 1) {
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
        }
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
    }

    private void initCombinedChart(CombinedChart chart, String[] h_data, int legent) {

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setDrawValueAboveBar(false);
        chart.setHighlightFullBarEnabled(false);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if(e instanceof BarEntry) {
                    BarEntry entry = (BarEntry) e;

                    if (entry.getYVals() != null) {
                        if (entry.getYVals().length > 1) {
                            Toast.makeText(getActivity(), getString(R.string.chart_desc_val) + " " + entry.getYVals()[h.getStackIndex()], Toast.LENGTH_SHORT).show();
                            //Snackbar.make(parent_view, getString(R.string.chart_desc_val) +" "+ entry.getYVals()[h.getStackIndex()], Snackbar.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.chart_desc_val) + " " + entry.getY(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(parent_view, getString(R.string.chart_desc_val) +" "+ entry.getY(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getActivity(), getString(R.string.chart_desc_val) + " " + e.getY(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected() {

            }
        });


        // draw bars behind lines
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.LINE
        });

        Legend l = chart.getLegend();
        l.setTypeface(tfLight);
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        chart.getAxisRight().setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setDrawGridLines(false);
        leftAxis.setTypeface(tfLight);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(0.5f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0.5f)
                    return h_data[0];
                if (value == 1.0f)
                    return h_data[1];
                return "";
            }
        });
    }
}