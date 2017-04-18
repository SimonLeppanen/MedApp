package com.example.simon.medapp;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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
                drawBPGraphs();
            }
        });

        setTodaysDate();
        drawBPGraphs();

    }

    private void drawBPGraphs() {


        setupLineGraph();
        setupBarGraph();
        setupGraphStyles();

        bpLineChart.invalidate(); // refresh
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
     * Sets up the gradient, colors, etc.
     */
    private void setupGraphStyles() {
        //Line graph
        int lineGraphHeight = bpLineChart.getHeight();
        Paint paint = bpLineChart.getRenderer().getPaintRender();
        LinearGradient linGrad = new LinearGradient(0, 0, 0, lineGraphHeight,
                getResources().getColor(R.color.colorCardHeader),
                getResources().getColor(R.color.colorCardHeader2),
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);

        //Bar graph
        int barGraphHeight = bpBarChart.getHeight();

    }

    /**
     * Creates graph, adds data
     */
    private void setupLineGraph() {
        bpLineChart = (LineChart) findViewById(R.id.linegraph_bp);
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(00, 52));
        entries.add(new Entry(01, 92));
        entries.add(new Entry(02, 149));
        entries.add(new Entry(03, 192));
        entries.add(new Entry(04, 89));
        entries.add(new Entry(05, 98));
        entries.add(new Entry(06, 102));
        entries.add(new Entry(07, 120));

        LineDataSet lineSet = new LineDataSet(entries, "BP");
        lineSet.setLineWidth(10);
        LineData lineData = new LineData(lineSet);
        bpLineChart.setData(lineData);
    }

    private void setupBarGraph() {
        bpBarChart = (BarChart) findViewById(R.id.bargraph_bp);

    }



}
