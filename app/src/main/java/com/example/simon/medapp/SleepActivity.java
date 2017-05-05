package com.example.simon.medapp;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.StrictMath.round;

/**
 * Created by simon on 2017-04-19.
 */

public class SleepActivity extends AppCompatActivity {

    Date date;
    private LineChart sleepLineChart;
    private BarChart sleepBarChart;
    private List<Entry> lineEntries1;
    private List<Entry> lineEntries2;
    private LineDataSet lineDataSet1;
    private LineDataSet lineDataSet2;
    private LineData lineData1;
    private LineData lineData2;
    private BarDataSet barDataSet;
    private BarData data;
    private List<BarEntry> barEntries;

    private int awakeColor;
    private int remColor;
    private int lightColor;
    private int deepColor;

    private String currentTotalSleptString = "06:38";
    private String currentAwakeString = "00:28";
    private String currentRemString = "02:26";
    private String currentLightString = "01:58";
    private String currentDeepString = "02:51";

    private float currentTotalSlept;
    private float currentAwake;
    private float currentRem;
    private float currentLight;
    private float currentDeep;

    private TextView totalNbr;
    private TextView totalText;
    private TextView remNbr;
    private TextView remText;
    private TextView awakeNbr;
    private TextView awakeText;
    private TextView lightNbr;
    private TextView lightText;
    private TextView deepNbr;
    private TextView deepText;
    private boolean firstSet;

    private Calendar activeDate;
    private LineDataSet lineDataSetCurr;
    private boolean todayClickedTwice;
    private boolean todayClickedOnce;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));
        //Runs graph to be able to create layout
        findViewById(R.id.linegraph_sleep).post(new Runnable() {
            @Override
            public void run() {
                drawSleepGraphs();
            }
        });
        findViewById(R.id.bargraph_sleep).post(new Runnable() {
            @Override
            public void run() {
                drawSleepGraphs();
            }
        });
        firstSet = true;
        activeDate = Calendar.getInstance();
        getTextViews();
        setDateInActivityMethod(activeDate);
        drawSleepGraphs();
    }


    private void toggleDateBox(Calendar calendar) {

        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(Methods.getDay(activeDate));
        dateBox.setText(Methods.getDate(activeDate));
    }

    private void setDateInActivityMethod(Calendar cal) {


        if (sleepLineChart != null) {
            sleepLineChart.highlightValues(null);
        }
        if (sleepBarChart != null) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
            String[] days = getWeekDayArray();
            for (int d = 0; d < days.length; d++) {
                if (dayFormat.format(cal.getTime()).toUpperCase().equals(days[d])) {
                    sleepBarChart.highlightValue(d + 1, 0, true);


                }
            }
        }
    }

    private void getTextViews() {
        totalNbr = (TextView) findViewById(R.id.total_nbr);
        totalText = (TextView) findViewById(R.id.total_text);
        awakeNbr = (TextView) findViewById(R.id.awake_nbr);
        awakeText = (TextView) findViewById(R.id.awake_text);
        remNbr = (TextView) findViewById(R.id.rem_nbr);
        remText = (TextView) findViewById(R.id.rem_text);
        lightNbr = (TextView) findViewById(R.id.light_nbr);
        lightText = (TextView) findViewById(R.id.light_text);
        deepNbr = (TextView) findViewById(R.id.deep_nbr);
        deepText = (TextView) findViewById(R.id.deep_text);
    }



    private void drawSleepGraphs() {
        setupLineEntries();
        setupLineGraph();
        setupGradient();

        setupBarEntries();
        setupBarGraph();

        sleepLineChart.invalidate(); // refresh
        sleepBarChart.invalidate(); // refresh
    }

    private void setupGradient() {

        ViewPortHandler v = sleepLineChart.getViewPortHandler();
        float chartHeight = v.contentHeight();
        float viewHeight = v.getChartHeight();
        float y0 = v.contentTop();
        float y1 = v.contentBottom();


        int graphHeight = sleepLineChart.getHeight();
        float grad = .1f;

        awakeColor = ContextCompat.getColor(this, R.color.sleep_awake);
        remColor = ContextCompat.getColor(this, R.color.sleep_rem);
        lightColor = ContextCompat.getColor(this, R.color.sleep_light);
        deepColor = ContextCompat.getColor(this, R.color.sleep_deep);

        int[] colors = {awakeColor, awakeColor, remColor, remColor, lightColor, lightColor, deepColor, deepColor};

        float[] positions = {0f, .25f - grad, .25f + grad, .5f - grad, .5f + grad, .75f - grad, .75f + grad, 1f};

        LinearGradient linGrad = new LinearGradient(0f, y0, 0f, y1,
                colors,
                positions,
                Shader.TileMode.REPEAT);

        Paint paint = sleepLineChart.getRenderer().getPaintRender();
        paint.setShader(linGrad);

    }

    private void setupLineEntries() {
        lineEntries1 = new ArrayList<>();
        lineEntries1.add(new Entry(0, 4));
        lineEntries1.add(new Entry(1, 4));
        lineEntries1.add(new Entry(2, 4));
        lineEntries1.add(new Entry(3, 3));
        lineEntries1.add(new Entry(4, 3));
        lineEntries1.add(new Entry(5, 3));
        lineEntries1.add(new Entry(6, 2));
        lineEntries1.add(new Entry(7, 2));
        lineEntries1.add(new Entry(8, 2));
        lineEntries1.add(new Entry(9, 3));
        lineEntries1.add(new Entry(10, 3));
        lineEntries1.add(new Entry(11, 3));
        lineEntries1.add(new Entry(12, 3));
        lineEntries1.add(new Entry(13, 3));
        lineEntries1.add(new Entry(14, 3));
        lineEntries1.add(new Entry(15, 2));
        lineEntries1.add(new Entry(16, 1));
        lineEntries1.add(new Entry(17, 1));
        lineEntries1.add(new Entry(18, 1));
        lineEntries1.add(new Entry(19, 2));
        lineEntries1.add(new Entry(20, 2));
        lineEntries1.add(new Entry(21, 3));
        lineEntries1.add(new Entry(22, 4));

        lineEntries2 = new ArrayList<>();
        lineEntries2.add(new Entry(0, 4));
        lineEntries2.add(new Entry(1, 4));
        lineEntries2.add(new Entry(2, 3));
        lineEntries2.add(new Entry(3, 3));
        lineEntries2.add(new Entry(4, 3));
        lineEntries2.add(new Entry(5, 2));
        lineEntries2.add(new Entry(6, 2));
        lineEntries2.add(new Entry(7, 2));
        lineEntries2.add(new Entry(8, 1));
        lineEntries2.add(new Entry(9, 1));
        lineEntries2.add(new Entry(10, 1));
        lineEntries2.add(new Entry(11, 1));
        lineEntries2.add(new Entry(12, 2));
        lineEntries2.add(new Entry(13, 2));
        lineEntries2.add(new Entry(14, 3));
        lineEntries2.add(new Entry(15, 3));
        lineEntries2.add(new Entry(16, 3));
        lineEntries2.add(new Entry(17, 2));
        lineEntries2.add(new Entry(18, 2));
        lineEntries2.add(new Entry(19, 2));
        lineEntries2.add(new Entry(20, 3));
        lineEntries2.add(new Entry(21, 4));
        lineEntries2.add(new Entry(22, 4));

    }

    private void setupLineGraph() {
        sleepLineChart = (LineChart) findViewById(R.id.linegraph_sleep);
        lineDataSet1 = new LineDataSet(lineEntries1, "");
        lineDataSet2 = new LineDataSet(lineEntries2, "");
        lineData1 = new LineData(lineDataSet1);
        lineData2 = new LineData(lineDataSet2);
        sleepLineChart.setData(lineData1);


        // Set style on graph bg
        sleepLineChart.getAxisLeft().setDrawGridLines(false);
        sleepLineChart.getAxisRight().setDrawGridLines(false);
        sleepLineChart.getXAxis().setDrawGridLines(false);
        sleepLineChart.setDrawGridBackground(false);
        sleepLineChart.setDrawBorders(false);
        Description description = new Description();
        description.setText("");
        sleepLineChart.setDescription(description);
        Legend legend = sleepLineChart.getLegend();
        legend.setEnabled(false);

        // Y-axis
        YAxis yAxisLeft = sleepLineChart.getAxisLeft();
        YAxis yAxisRight = sleepLineChart.getAxisRight();
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(5f);
        yAxisLeft.setGranularity(1f);
        yAxisLeft.setValueFormatter(new SleepValueFormatter());
        yAxisRight.setAxisMinimum(0f);
        yAxisRight.setAxisMaximum(5f);
        yAxisRight.setGranularity(1f);
        yAxisRight.setValueFormatter(new SleepValueFormatter());

        // X-axis
        XAxis xAxis = sleepLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(2, true);
        xAxis.setValueFormatter(new SleepXAxisValueFormatter());
        LineData lineData = new LineData(lineDataSet1);
        sleepLineChart.setData(lineData);

        //Highlighter
        lineDataSet1.setHighlightEnabled(false);

        // Zoom disabled
        sleepLineChart.setScaleEnabled(false);

        // Style of line
        lineDataSet1.setLineWidth(10);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setDrawValues(false);
        lineDataSet1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet2.setLineWidth(10);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        //Limitlines
        LimitLine line1 = new LimitLine(1.5f);
        line1.enableDashedLine(20f, 20f, 0f);
        line1.setLineColor(ContextCompat.getColor(this, R.color.default_gray));
        yAxisLeft.addLimitLine(line1);

        LimitLine line2 = new LimitLine(2.5f);
        line2.enableDashedLine(20f, 20f, 0f);
        line2.setLineColor(ContextCompat.getColor(this, R.color.default_gray));
        yAxisLeft.addLimitLine(line2);

        LimitLine line3 = new LimitLine(3.5f);
        line3.enableDashedLine(20f, 20f, 0f);
        line3.setLineColor(ContextCompat.getColor(this, R.color.default_gray));
        yAxisLeft.addLimitLine(line3);


    }

    private void setupBarEntries() {
     /*
        List<BarEntry> barEntriesAwake = new ArrayList<>();
        barEntriesAwake.add(new BarEntry(1, 10f));
        barEntriesAwake.add(new BarEntry(2, 12f));
        barEntriesAwake.add(new BarEntry(3, 15f));
        barEntriesAwake.add(new BarEntry(4, 3f));
        barEntriesAwake.add(new BarEntry(5, 17f));
        barEntriesAwake.add(new BarEntry(6, 13f));
        barEntriesAwake.add(new BarEntry(7, 11f));

        List<BarEntry> barEntriesREM = new ArrayList<>();
        barEntriesREM.add(new BarEntry(1, 14f));
        barEntriesREM.add(new BarEntry(2, 11f));
        barEntriesREM.add(new BarEntry(3, 17f));
        barEntriesREM.add(new BarEntry(4, 13f));
        barEntriesREM.add(new BarEntry(5, 19f));
        barEntriesREM.add(new BarEntry(6, 12f));
        barEntriesREM.add(new BarEntry(7, 14f));

        List<BarEntry> barEntriesLight = new ArrayList<>();
        barEntriesLight.add(new BarEntry(1,32f));
        barEntriesLight.add(new BarEntry(2,37f));
        barEntriesLight.add(new BarEntry(3,33f));
        barEntriesLight.add(new BarEntry(4,28f));
        barEntriesLight.add(new BarEntry(5,41f));
        barEntriesLight.add(new BarEntry(6,39f));
        barEntriesLight.add(new BarEntry(7,33f));

        List<BarEntry> barEntriesDeep = new ArrayList<>();
        barEntriesDeep.add(new BarEntry(1,44f));
        barEntriesDeep.add(new BarEntry(2,40f));
        barEntriesDeep.add(new BarEntry(3,35f));
        barEntriesDeep.add(new BarEntry(4,56f));
        barEntriesDeep.add(new BarEntry(5,23f));
        barEntriesDeep.add(new BarEntry(6,24f));
        barEntriesDeep.add(new BarEntry(7,42f));*/

        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, new float[]{.46f, 2.45f, 1.97f, 2.87f}));
        barEntries.add(new BarEntry(2, new float[]{.49f, .56f, 3.23f, 3.87f}));
        barEntries.add(new BarEntry(3, new float[]{.65f, .74f, 3.12f, 4.21f}));
        barEntries.add(new BarEntry(4, new float[]{.12f, 1.2f, 2.43f, 2.67f}));
        barEntries.add(new BarEntry(5, new float[]{.72f, .98f, 2.69f, 1.98f}));
        barEntries.add(new BarEntry(6, new float[]{.48f, .30f, 3.19f, 2.87f}));
        barEntries.add(new BarEntry(7, new float[]{.39f, .93f, 3.78f, 3.83f}));
    }

    private void setupBarGraph() {
        sleepBarChart = (BarChart) findViewById(R.id.bargraph_sleep);
        barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(deepColor, lightColor, remColor, awakeColor);
        data = new BarData(barDataSet);
        sleepBarChart.setData(data);

        // Set style on graph bg
        sleepBarChart.getAxisLeft().setDrawGridLines(false);
        sleepBarChart.getAxisRight().setDrawGridLines(false);
        sleepBarChart.getXAxis().setDrawGridLines(false);
        sleepBarChart.setDrawGridBackground(false);
        sleepBarChart.setDrawBorders(false);
        sleepBarChart.setDrawValueAboveBar(false);
        Description description = new Description();
        description.setText("");
        sleepBarChart.setDescription(description);
        barDataSet.setDrawValues(false);
        Legend legend = sleepBarChart.getLegend();
        legend.setEnabled(false);
        sleepBarChart.setScaleEnabled(false);
        sleepBarChart.setHighlightFullBarEnabled(true);

        //Highlight
        barDataSet.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter2));
        barDataSet.setHighLightAlpha(150);
        sleepBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int toAdd = 7-(int) h.getX();
                Calendar thisDate = Calendar.getInstance();
                thisDate.add(Calendar.DATE,-toAdd);
                activeDate = thisDate;
                toggleDateBox(thisDate);

                BarEntry b = (BarEntry) e;
                int color = getResources().getColor(R.color.highlighter2);
                int colorInactive = getResources().getColor(R.color.default_gray);

                currentTotalSlept = b.getPositiveSum();
                currentAwake = b.getYVals()[0];
                currentRem = b.getYVals()[1];
                currentLight = b.getYVals()[2];
                currentDeep = b.getYVals()[3];

                if (h.getX() == (float) 7) {
                    sleepBarChart.highlightValues(null);
                    totalNbr.setTextColor(colorInactive);

                    totalNbr.setTextColor(colorInactive);
                    awakeNbr.setTextColor(colorInactive);
                    remNbr.setTextColor(colorInactive);
                    lightNbr.setTextColor(colorInactive);
                    deepNbr.setTextColor(colorInactive);
                }

                if (!(h.getX() == (float) 7)) {
                    totalNbr.setTextColor(color);
                    awakeNbr.setTextColor(color);
                    remNbr.setTextColor(color);
                    lightNbr.setTextColor(color);
                    deepNbr.setTextColor(color);
                }


                totalNbr.setText(getInTime(currentTotalSlept));
                awakeNbr.setText(getInTime(currentAwake));
                remNbr.setText(getInTime(currentRem));
                lightNbr.setText(getInTime(currentLight));
                deepNbr.setText(getInTime(currentDeep));


            }

            @Override
            public void onNothingSelected() {
                switchSet();
                int color = getResources().getColor(R.color.default_gray);
                totalNbr.setText(currentTotalSleptString);
                awakeNbr.setText(currentAwakeString);
                remNbr.setText(currentRemString);
                lightNbr.setText(currentLightString);
                deepNbr.setText(currentDeepString);

                totalNbr.setTextColor(color);
                awakeNbr.setTextColor(color);
                remNbr.setTextColor(color);
                lightNbr.setTextColor(color);
                deepNbr.setTextColor(color);
            }
        });

        YAxis yAxisLeft = sleepBarChart.getAxisLeft();
        YAxis yAxisRight = sleepBarChart.getAxisRight();
        float max = barDataSet.getYMax() + 1;
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(round(max));
        yAxisLeft.setValueFormatter(new HourAxisFormatter());

        yAxisRight.setAxisMinimum(0f);
        yAxisRight.setAxisMaximum(round(max));
        yAxisRight.setValueFormatter(new HourAxisFormatter());

        XAxis xAxis = sleepBarChart.getXAxis();
        xAxis.setValueFormatter(new DayValueFormatter(getWeekDayArray()));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private String[] getWeekDayArray() {
        String[] days = new String[7];
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
        today.add(Calendar.DATE, -7);
        for (int i = 0; i < 7; i++) {
            today.add(Calendar.DATE, +1);
            days[i] = dayFormat.format(today.getTime()).toUpperCase();
        }
        return days;
    }

    private void switchSet() {
        if (DateUtils.isToday(activeDate.getTimeInMillis())) {
            if (!todayClickedTwice) {
                todayClickedOnce = false;
                todayClickedTwice = false;
                switchSetHelper();
            }
        } else {
            switchSetHelper();
        }
    }

    private void switchSetHelper() {
        if (firstSet) {
            firstSet = false;
            lineDataSetCurr = lineDataSet2;
            //avgSyst.setText(avgSystOrig2);
            //avgDiast.setText(avgDiastOrig2);


            sleepLineChart.setData(lineData2);
            sleepLineChart.invalidate();

        } else {
            firstSet = true;
            lineDataSetCurr = lineDataSet1;
            //avgSyst.setText(nowSystOrig1);
            //avgDiast.setText(nowDiastOrig1);


            sleepLineChart.setData(lineData1);
            sleepLineChart.invalidate();

        }
    }


    private String getInTime(float value) {

        DecimalFormat formatter = new DecimalFormat("00");
        int hours = (int) value;
        int minutes = (int) (value * 60) % 60;
        String hoursString = formatter.format(hours);
        String minutesString = formatter.format(minutes);

        return hoursString + ":" + minutesString;

    }


    public void dateChange(View view) {
        if (view == findViewById(R.id.sleep_dateBackButton)) {
            activeDate.add(Calendar.DATE, -1);
            setDateInActivityMethod(activeDate);
        }
        if (view == findViewById(R.id.sleep_dateForwardButton)) {
            if (!DateUtils.isToday(activeDate.getTimeInMillis())) {
                activeDate.add(Calendar.DATE, +1);
                setDateInActivityMethod(activeDate);
            }
            if (DateUtils.isToday(activeDate.getTimeInMillis())) {
                //nowSyst.setText(nowSystOrig1);
                //nowDiast.setText(nowDiastOrig1);
            }
        }
    }
}


class SleepValueFormatter implements IAxisValueFormatter {

    String[] states;

    public SleepValueFormatter() {
        states = new String[]{"", "Deep", "Light", "REM", "Awake",""};
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        return states[(int) value];
    }
}

class HourAxisFormatter implements IAxisValueFormatter {

    DecimalFormat formatter;
    public HourAxisFormatter() {
        formatter = new DecimalFormat("00");
    }
    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int hours = (int) value;
        int minutes = (int) (value * 60) % 60;
        String hoursString = formatter.format(hours);
        String minutesString = formatter.format(minutes);
        //return hoursString + ":" + minutesString;
        return Integer.toString(hours) + "h";
    }
}

class SleepXAxisValueFormatter implements IAxisValueFormatter {
    boolean first;
    public SleepXAxisValueFormatter() {
        first = true;
    }

    public String getFormattedValue(float value, AxisBase axis) {
        if (value == 0f) {
            return "22:38";
        }
        return "06:29";
    }

}