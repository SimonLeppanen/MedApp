package com.example.simon.medapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 2017-04-19.
 */

public class SleepActivity extends AppCompatActivity {

    Date date;
    private LineChart sleepLineChart;
    private BarChart sleepBarChart;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        //Runs graph to be able to create layout
        findViewById(R.id.linegraph_sleep).post(new Runnable() {
            @Override
            public void run() {
                setupLineGraph();
            }
        });
        findViewById(R.id.bargraph_sleep).post(new Runnable() {
            @Override
            public void run() {
                setupBarGraph();
            }
        });

        setTodaysDate();
        drawSleepGraphs();
    }

    private void setTodaysDate() {
        // NOT FINISHED!
        date = new Date();
        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(Methods.getDay(date));
        dateBox.setText(Methods.getDate(date));
    }

    private void drawSleepGraphs() {


        setupLineGraph();
        setupBarGraph();

        sleepLineChart.invalidate(); // refresh
        sleepBarChart.invalidate(); // refresh
    }

    private void setupLineGraph() {
        sleepLineChart = (LineChart) findViewById(R.id.linegraph_sleep);

        List<Entry> entries_awake = new ArrayList<>();
        entries_awake.add(new Entry(00, 132));
        entries_awake.add(new Entry(01, 131));
        entries_awake.add(new Entry(02, 123));
        entries_awake.add(new Entry(03, 131));
        entries_awake.add(new Entry(04, 121));
        entries_awake.add(new Entry(05, 143));
        entries_awake.add(new Entry(06, 138));
        entries_awake.add(new Entry(07, 135));
        LineDataSet lineSet_awake = new LineDataSet(entries_awake, "Awake");
        lineSet_awake.setColor(getResources().getColor(R.color.sleep_awake));
        lineSet_awake.setLineWidth(5);

        List<Entry> entries_rem = new ArrayList<>();
        entries_rem.add(new Entry(00, 71));
        entries_rem.add(new Entry(01, 77));
        entries_rem.add(new Entry(02, 67));
        entries_rem.add(new Entry(03, 81));
        entries_rem.add(new Entry(04, 71));
        entries_rem.add(new Entry(05, 74));
        entries_rem.add(new Entry(06, 78));
        entries_rem.add(new Entry(07, 72));
        LineDataSet lineSet_rem = new LineDataSet(entries_rem, "REM");
        lineSet_rem.setColor(getResources().getColor(R.color.sleep_rem));
        lineSet_rem.setLineWidth(5);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineSet_awake);
        dataSets.add(lineSet_rem);

        LineData lineData = new LineData(dataSets);
        sleepLineChart.setData(lineData);
    }

    private void setupBarGraph() {
        sleepBarChart = (BarChart) findViewById(R.id.bargraph_sleep);
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(01, new float[]{71,132}));
        entries.add(new BarEntry(02, new float[]{77,131}));
        entries.add(new BarEntry(03, new float[]{81,123}));
        entries.add(new BarEntry(04, new float[]{71,131}));
        entries.add(new BarEntry(05, new float[]{74,121}));
        entries.add(new BarEntry(06, new float[]{78,138}));
        entries.add(new BarEntry(07, new float[]{72,135}));

        BarDataSet dataSet = new BarDataSet(entries, "BP");
        dataSet.setColors(getResources().getColor(R.color.sleep_rem), getResources().getColor(R.color.sleep_awake));
        BarData data = new BarData(dataSet);
        sleepBarChart.setData(data);
    }





}
