package com.example.simon.medapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * Created by simon on 2017-04-18.
 */

public class BPActivity extends AppCompatActivity {

    private Calendar activeDate;
    private Calendar today;
    private LineChart bpLineChart;
    private BarChart bpBarChart;

    private List<Entry> entries_syst1;
    private List<Entry> entries_diast1;
    private List<Entry> entries_syst2;
    private List<Entry> entries_diast2;

    private LineDataSet lineSet_syst1;
    private LineDataSet lineSet_diast1;
    private LineDataSet lineSet_syst2;
    private LineDataSet lineSet_diast2;

    private LineDataSet lineSet_syst_curr;
    private LineDataSet lineSet_diast_curr;

    private List<BarEntry> entriesBar;
    private List<BarEntry> entriesBar_syst1;
    private List<BarEntry> entriesBar_diast1;
    private List<BarEntry> entriesBar_syst2;
    private List<BarEntry> entriesBar_diast2;
    private BarDataSet barDataSet;
    private BarDataSet barDataSet_syst;
    private BarDataSet barDataSet_diast;

    private String avgSystOrig;
    private String avgDiastOrig;
    private String nowSystOrig;
    private String nowDiastOrig;
    private String nowTextOrig;

    private TextView avgSyst;
    private TextView avgDiast;
    private TextView avgText;
    private TextView nowSyst;
    private TextView nowDiast;
    private TextView nowText;

    private Highlight lineHighlight;
    private boolean barSelected;
    private boolean lineHighlightActive;
    private boolean firstSetActive;
    private LineData lineData1;
    private LineData lineData2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));

        //Runs graph to be able to create layout
        findViewById(R.id.linegraph_bp).post(new Runnable() {
            @Override
            public void run() {
                setupLineEntries();
            }
        });
        findViewById(R.id.bargraph_bp).post(new Runnable() {
            @Override
            public void run() {
                setupBarEntries();
            }
        });
        today = Calendar.getInstance();
        activeDate = Calendar.getInstance();
        firstSetActive = true;
        getTextViews();
        setDateInActivity(Calendar.getInstance());
        drawGraphs();

    }

    private void getTextViews() {
        avgSystOrig = "135";
        avgDiastOrig = "72";
        nowSystOrig = "131";
        nowDiastOrig = "71";
        nowTextOrig = "NOW";
        barSelected = false;
        lineHighlightActive = false;

        avgSyst = (TextView) findViewById(R.id.right_syst_nbr);
        avgDiast = (TextView) findViewById(R.id.right_diast_nbr);
        avgText = (TextView) findViewById(R.id.bp_right_text);
        nowSyst = (TextView) findViewById(R.id.left_syst_nbr);
        nowDiast = (TextView) findViewById(R.id.left_diast_nbr);
        nowText = (TextView) findViewById(R.id.bp_left_text);
    }


    private void drawGraphs() {


        setupLineEntries();
        setupLineGraph();
        setupBarEntries();
        setupBarGraph();

        bpLineChart.invalidate();
        bpBarChart.invalidate();// refresh
    }


    private void setDateInActivity(Calendar cal) {
        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(Methods.getDay(cal));
        dateBox.setText(Methods.getDate(cal));
    }

    /**
     * Creates graph, adds data
     */
    private void setupLineEntries() {

        entries_syst1 = new ArrayList<>();
        entries_syst1.add(new Entry(00, 132));
        entries_syst1.add(new Entry(01, 131));
        entries_syst1.add(new Entry(02, 123));
        entries_syst1.add(new Entry(03, 131));
        entries_syst1.add(new Entry(04, 121));
        entries_syst1.add(new Entry(05, 143));
        entries_syst1.add(new Entry(06, 138));
        entries_syst1.add(new Entry(07, 135));

        entries_diast1 = new ArrayList<>();
        entries_diast1.add(new Entry(00, 71));
        entries_diast1.add(new Entry(01, 77));
        entries_diast1.add(new Entry(02, 67));
        entries_diast1.add(new Entry(03, 81));
        entries_diast1.add(new Entry(04, 71));
        entries_diast1.add(new Entry(05, 74));
        entries_diast1.add(new Entry(06, 78));
        entries_diast1.add(new Entry(07, 72));

        entries_syst2 = new ArrayList<>();
        entries_syst2.add(new Entry(00, 122));
        entries_syst2.add(new Entry(01, 121));
        entries_syst2.add(new Entry(02, 133));
        entries_syst2.add(new Entry(03, 121));
        entries_syst2.add(new Entry(04, 131));
        entries_syst2.add(new Entry(05, 133));
        entries_syst2.add(new Entry(06, 128));
        entries_syst2.add(new Entry(07, 125));

        entries_diast2 = new ArrayList<>();
        entries_diast2.add(new Entry(00, 74));
        entries_diast2.add(new Entry(01, 73));
        entries_diast2.add(new Entry(02, 64));
        entries_diast2.add(new Entry(03, 86));
        entries_diast2.add(new Entry(04, 74));
        entries_diast2.add(new Entry(05, 73));
        entries_diast2.add(new Entry(06, 72));
        entries_diast2.add(new Entry(07, 71));


    }

    private void setupLineGraph() {

        bpLineChart = (LineChart) findViewById(R.id.linegraph_bp);

        lineSet_syst1 = new LineDataSet(entries_syst1, "Systolic");
        lineSet_diast1 = new LineDataSet(entries_diast1, "Diastolic");

        lineSet_syst2 = new LineDataSet(entries_syst2, "Systolic");
        lineSet_diast2 = new LineDataSet(entries_diast2, "Diastolic");


        lineSet_syst1.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineSet_diast1.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineSet_syst2.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineSet_diast2.setAxisDependency(YAxis.AxisDependency.LEFT);

        lineSet_syst_curr = lineSet_syst1;
        lineSet_diast_curr = lineSet_diast1;

        List<ILineDataSet> dataSets1 = new ArrayList<>();
        dataSets1.add(lineSet_syst1);
        dataSets1.add(lineSet_diast1);

        List<ILineDataSet> dataSets2 = new ArrayList<>();
        dataSets2.add(lineSet_syst2);
        dataSets2.add(lineSet_diast2);

        lineData1 = new LineData(dataSets1);
        lineData2 = new LineData(dataSets2);


        // Set style on graph bg
        bpLineChart.getAxisLeft().setDrawGridLines(false);
        bpLineChart.getAxisRight().setDrawGridLines(false);
        bpLineChart.getXAxis().setDrawGridLines(false);
        bpLineChart.setDrawGridBackground(false);
        bpLineChart.setDrawBorders(false);
        Description description = new Description();
        description.setText("");
        bpLineChart.setDescription(description);
        Legend legend = bpLineChart.getLegend();
        legend.setEnabled(false);

        // Y-axis
        YAxis yAxisLeft = bpLineChart.getAxisLeft();
        YAxis yAxisRight = bpLineChart.getAxisRight();
        yAxisLeft.setAxisMinimum(50f);
        yAxisLeft.setAxisMaximum(170f);
        yAxisRight.setAxisMinimum(50f);
        yAxisRight.setAxisMaximum(170f);


        // X-axis
        XAxis xAxis = bpLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //Highlighter
        lineSet_syst1.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter1));
        lineSet_syst1.setDrawHorizontalHighlightIndicator(false);
        lineSet_syst1.setHighlightLineWidth(3f);

        lineSet_diast1.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter1));
        lineSet_diast1.setDrawHorizontalHighlightIndicator(false);
        lineSet_diast1.setHighlightLineWidth(3f);

        lineSet_syst2.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter1));
        lineSet_syst2.setDrawHorizontalHighlightIndicator(false);
        lineSet_syst2.setHighlightLineWidth(3f);

        lineSet_diast2.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter1));
        lineSet_diast2.setDrawHorizontalHighlightIndicator(false);
        lineSet_diast2.setHighlightLineWidth(3f);

        bpLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                lineHighlightActive = true;
                int color = getResources().getColor(R.color.highlighter1);
                int index = (int) e.getX();
                Entry eSyst = lineSet_syst_curr.getEntryForIndex(index);
                Entry eDiast = lineSet_diast_curr.getEntryForIndex(index);
                setGreyedOut(false);
                nowSyst.setText(Integer.toString((int) eSyst.getY()));
                nowSyst.setTextColor(color);

                nowDiast.setText(Integer.toString((int) eDiast.getY()));
                nowDiast.setTextColor(color);

                nowText.setText("CURSOR");
                nowText.setTextColor(color);
            }

            @Override
            public void onNothingSelected() {
                lineHighlightActive = false;
                int color = getResources().getColor(R.color.default_gray);
                if (barSelected) {
                    setGreyedOut(true);
                } else if (!barSelected) {
                    setGreyedOut(false);
                }
                nowSyst.setText(nowSystOrig);
                nowSyst.setTextColor(color);
                nowDiast.setText(nowDiastOrig);
                nowDiast.setTextColor(color);
                nowText.setText(nowTextOrig);
                nowText.setTextColor(color);

            }
        });

        // Zoom disabled
        bpLineChart.setScaleEnabled(false);

        // Style of line
        lineSet_syst1.setLineWidth(5);
        lineSet_diast1.setLineWidth(5);

        lineSet_syst1.setDrawCircles(false);
        lineSet_diast1.setDrawCircles(false);

        lineSet_syst1.setDrawValues(false);
        lineSet_diast1.setDrawValues(false);

        lineSet_syst1.setColor(ContextCompat.getColor(this, R.color.BPsyst));
        lineSet_diast1.setColor(ContextCompat.getColor(this, R.color.BPdiast));

        lineSet_syst1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineSet_diast1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        lineSet_syst2.setLineWidth(5);
        lineSet_diast2.setLineWidth(5);

        lineSet_syst2.setDrawCircles(false);
        lineSet_diast2.setDrawCircles(false);

        lineSet_syst2.setDrawValues(false);
        lineSet_diast2.setDrawValues(false);

        lineSet_syst2.setColor(ContextCompat.getColor(this, R.color.BPsyst));
        lineSet_diast2.setColor(ContextCompat.getColor(this, R.color.BPdiast));

        lineSet_syst2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineSet_diast2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Initiate graph
        bpLineChart.setData(lineData1);

    }
    private void switchSet() {
        if (firstSetActive) {
            firstSetActive = false;
            lineSet_syst_curr = lineSet_syst2;
            lineSet_diast_curr = lineSet_diast2;
            bpLineChart.setData(lineData2);
            bpLineChart.invalidate();
        } else {
            firstSetActive = true;
            lineSet_syst_curr = lineSet_syst1;
            lineSet_diast_curr = lineSet_diast1;
            bpLineChart.setData(lineData1);
            bpLineChart.invalidate();
        }
    }

    private void setGreyedOut(boolean b) {
        if (!b) {
            nowSyst.setAlpha(1f);
            nowDiast.setAlpha(1f);
            nowText.setAlpha(1f);
        }
        if (b) {
            nowSyst.setAlpha(.33f);
            nowDiast.setAlpha(.33f);
            nowText.setAlpha(.33f);
        }

    }

    private void setupBarEntries() {
        entriesBar_syst1 = new ArrayList<>();
        entriesBar_syst1.add(new BarEntry(1, 132));
        entriesBar_syst1.add(new BarEntry(2, 131));
        entriesBar_syst1.add(new BarEntry(3, 123));
        entriesBar_syst1.add(new BarEntry(4, 131));
        entriesBar_syst1.add(new BarEntry(5, 121));
        entriesBar_syst1.add(new BarEntry(6, 138));
        entriesBar_syst1.add(new BarEntry(7, 135));

        entriesBar_diast1 = new ArrayList<>();
        entriesBar_diast1.add(new BarEntry(1, 71));
        entriesBar_diast1.add(new BarEntry(2, 77));
        entriesBar_diast1.add(new BarEntry(3, 81));
        entriesBar_diast1.add(new BarEntry(4, 71));
        entriesBar_diast1.add(new BarEntry(5, 74));
        entriesBar_diast1.add(new BarEntry(6, 78));
        entriesBar_diast1.add(new BarEntry(7, 72));

        entriesBar_syst2 = new ArrayList<>();
        entriesBar_syst2.add(new BarEntry(1, 122));
        entriesBar_syst2.add(new BarEntry(2, 131));
        entriesBar_syst2.add(new BarEntry(3, 133));
        entriesBar_syst2.add(new BarEntry(4, 121));
        entriesBar_syst2.add(new BarEntry(5, 131));
        entriesBar_syst2.add(new BarEntry(6, 128));
        entriesBar_syst2.add(new BarEntry(7, 125));

        entriesBar_diast2 = new ArrayList<>();
        entriesBar_diast2.add(new BarEntry(1, 74));
        entriesBar_diast2.add(new BarEntry(2, 72));
        entriesBar_diast2.add(new BarEntry(3, 84));
        entriesBar_diast2.add(new BarEntry(4, 72));
        entriesBar_diast2.add(new BarEntry(5, 73));
        entriesBar_diast2.add(new BarEntry(6, 75));
        entriesBar_diast2.add(new BarEntry(7, 73));


        /*entriesBar.add(new BarEntry(1, new float[]{71,132}));
        entriesBar.add(new BarEntry(2, new float[]{77,131}));
        entriesBar.add(new BarEntry(3, new float[]{81,123}));
        entriesBar.add(new BarEntry(4, new float[]{71,131}));
        entriesBar.add(new BarEntry(5, new float[]{74,121}));
        entriesBar.add(new BarEntry(6, new float[]{78,138}));
        entriesBar.add(new BarEntry(7, new float[]{72,135}));*/
    }

    private void setupBarGraph() {
        bpBarChart = (BarChart) findViewById(R.id.bargraph_bp);
        barDataSet_syst = new BarDataSet(entriesBar_syst1, "BP");
        barDataSet_diast = new BarDataSet(entriesBar_diast1, "BP");

        // Zoom and hide values
        bpBarChart.setScaleEnabled(false);
        barDataSet_syst.setDrawValues(false);
        barDataSet_diast.setDrawValues(false);


        barDataSet_syst.setColor(ContextCompat.getColor(this, R.color.BPsyst));
        barDataSet_diast.setColor(ContextCompat.getColor(this, R.color.BPdiast));
        //barDataSet.setColors(getResources().getColor(R.color.BPsyst), getResources().getColor(R.color.BPdiast));
        ArrayList<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();
        barDataSets.add(barDataSet_syst);
        barDataSets.add(barDataSet_diast);
        //BarData barData = new BarData(barDataSet_syst);
        //barData.addDataSet(barDataSet_diast);
        BarData barData = new BarData(barDataSets);
        bpBarChart.setData(barData);

        Log.d("Nbr of sets", Integer.toString(barData.getDataSetCount()));

        // Highlight
        barDataSet_syst.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter2));
        barDataSet_diast.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter2));
        barDataSet_syst.setHighLightAlpha(150);
        barDataSet_diast.setHighLightAlpha(150);
        bpBarChart.setHighlightFullBarEnabled(true);
        bpBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {

                bpLineChart.highlightValues(null);
                barSelected = true;
                switchSet();

                BarEntry bSyst = (BarEntry) e;
                BarEntry bDiast = getDiastEntry(bSyst);
                int color = getResources().getColor(R.color.highlighter2);
                int colorInactive = getResources().getColor(R.color.default_gray);

                avgText.setTextColor(color);
                avgSyst.setText(Integer.toString((int) bSyst.getY()));
                avgSyst.setTextColor(color);
                avgDiast.setText(Integer.toString((int) bDiast.getY()));
                avgDiast.setTextColor(color);

                nowText.setText(nowTextOrig);
                nowText.setTextColor(colorInactive);
                nowSyst.setText(nowSystOrig);
                nowSyst.setTextColor(colorInactive);
                nowDiast.setText(nowDiastOrig);
                nowDiast.setTextColor(colorInactive);

                setGreyedOut(true);
            }


            @Override
            public void onNothingSelected() {
                bpLineChart.highlightValues(null);
                barSelected = false;

                int color = getResources().getColor(R.color.default_gray);

                avgText.setTextColor(color);
                avgSyst.setText(avgSystOrig);
                avgSyst.setTextColor(color);
                avgDiast.setText(avgDiastOrig);
                avgDiast.setTextColor(color);

                nowText.setText(nowTextOrig);
                nowText.setTextColor(color);
                nowSyst.setText(nowSystOrig);
                nowSyst.setTextColor(color);
                nowDiast.setText(nowDiastOrig);
                nowDiast.setTextColor(color);


                setGreyedOut(false);
            }
        });

        // Set style on graph bg
        bpBarChart.getAxisLeft().setDrawGridLines(false);
        bpBarChart.getAxisRight().setDrawGridLines(false);
        bpBarChart.getXAxis().setDrawGridLines(false);
        bpBarChart.setDrawGridBackground(false);
        bpBarChart.setDrawBorders(false);
        bpBarChart.setDrawValueAboveBar(false);
        Description description = new Description();
        description.setText("");
        bpBarChart.setDescription(description);
        Legend legend = bpBarChart.getLegend();
        legend.setEnabled(false);
        barDataSet_syst.setValueTextColor(Color.WHITE);
        barDataSet_diast.setValueTextColor(Color.WHITE);
        barDataSet_syst.setValueTextSize(10f);
        barDataSet_diast.setValueTextSize(10f);

        // Y-axis
        YAxis yAxisLeft = bpBarChart.getAxisLeft();
        YAxis yAxisRight = bpBarChart.getAxisRight();
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(170f);
        yAxisRight.setAxisMinimum(0f);
        yAxisRight.setAxisMaximum(170f);

        XAxis xAxis = bpBarChart.getXAxis();
        xAxis.setValueFormatter(new DayValueFormatter(getWeekDayArray()));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private BarEntry getDiastEntry(BarEntry b) {

        return barDataSet_diast.getEntryForIndex((int) b.getX() - 1);
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

    public void dateChange(View view) {
        if (view == findViewById(R.id.bp_dateBackButton)) {
            activeDate.add(Calendar.DATE,-1);
            setDateInActivity(activeDate);
            switchSet();
        }
        if (view == findViewById(R.id.bp_dateForwardButton)) {
            if (!DateUtils.isToday(activeDate.getTimeInMillis())) {
                activeDate.add(Calendar.DATE, +1);
                setDateInActivity(activeDate);
                switchSet();
            }
        }
    }
}



class DayValueFormatter implements IAxisValueFormatter {

    String[] days;

    public DayValueFormatter(String[] days) {
        this.days = days;

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        return days[(int) value - 1];

    }
}



