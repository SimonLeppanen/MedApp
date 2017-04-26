package com.example.simon.medapp;

import android.graphics.drawable.ColorDrawable;
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
 * Created by simon on 2017-04-18.
 */

public class BPActivity extends AppCompatActivity{

    Date date;
    private LineChart bpLineChart;
    private BarChart bpBarChart;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));

        //Runs graph to be able to create layout
        findViewById(R.id.linegraph_bp).post(new Runnable() {
            @Override
            public void run() {
                setupLineGraph();
            }
        });
        findViewById(R.id.bargraph_bp).post(new Runnable() {
            @Override
            public void run() {
                setupBarGraph();
            }
        });

        setTodaysDate();
        drawSleepGraphs();

    }

    private void drawSleepGraphs() {


        setupLineGraph();
        setupBarGraph();

        bpLineChart.invalidate();
        bpBarChart.invalidate();// refresh
    }



    private void setTodaysDate() {
        // NOT FINISHED!
        date = new Date();
        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(Methods.getDay(date));
        dateBox.setText(Methods.getDate(date));
    }

    /**
     * Creates graph, adds data
     */
    private void setupLineGraph() {
        bpLineChart = (LineChart) findViewById(R.id.linegraph_bp);

        List<Entry> entries_syst = new ArrayList<>();
        entries_syst.add(new Entry(00, 132));
        entries_syst.add(new Entry(01, 131));
        entries_syst.add(new Entry(02, 123));
        entries_syst.add(new Entry(03, 131));
        entries_syst.add(new Entry(04, 121));
        entries_syst.add(new Entry(05, 143));
        entries_syst.add(new Entry(06, 138));
        entries_syst.add(new Entry(07, 135));
        LineDataSet lineSet_syst = new LineDataSet(entries_syst, "Systolic");
        lineSet_syst.setColor(getResources().getColor(R.color.BPsyst));
        lineSet_syst.setLineWidth(5);

        List<Entry> entries_diast = new ArrayList<>();
        entries_diast.add(new Entry(00, 71));
        entries_diast.add(new Entry(01, 77));
        entries_diast.add(new Entry(02, 67));
        entries_diast.add(new Entry(03, 81));
        entries_diast.add(new Entry(04, 71));
        entries_diast.add(new Entry(05, 74));
        entries_diast.add(new Entry(06, 78));
        entries_diast.add(new Entry(07, 72));
        LineDataSet lineSet_diast = new LineDataSet(entries_diast, "Diastolic");
        lineSet_diast.setColor(getResources().getColor(R.color.BPdiast));
        lineSet_diast.setLineWidth(5);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineSet_syst);
        dataSets.add(lineSet_diast);

        LineData lineData = new LineData(dataSets);
        bpLineChart.setData(lineData);
    }

    private void setupBarGraph() {
        bpBarChart = (BarChart) findViewById(R.id.bargraph_bp);
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(01, new float[]{71,132}));
        entries.add(new BarEntry(02, new float[]{77,131}));
        entries.add(new BarEntry(03, new float[]{81,123}));
        entries.add(new BarEntry(04, new float[]{71,131}));
        entries.add(new BarEntry(05, new float[]{74,121}));
        entries.add(new BarEntry(06, new float[]{78,138}));
        entries.add(new BarEntry(07, new float[]{72,135}));

        BarDataSet dataSet = new BarDataSet(entries, "BP");
        dataSet.setColors(getResources().getColor(R.color.BPsyst), getResources().getColor(R.color.BPdiast));
        BarData data = new BarData(dataSet);
        bpBarChart.setData(data);
    }



}
