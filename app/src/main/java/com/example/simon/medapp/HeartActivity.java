package com.example.simon.medapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by simon on 2017-04-17.
 */


public class HeartActivity extends AppCompatActivity {

    private LineChart heartChart;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        findViewById(R.id.graph_heart).post(new Runnable() {
            @Override
            public void run() {
                drawHeartGraph();
            }
        });

        date = new Date();
        TextView dateBox = (TextView) findViewById(R.id.date);
        dateBox.setText(toString(date));


        drawHeartGraph();

    }

    public void drawHeartGraph() {

        setupGraphStyle();
        setupHeartGraph();

        heartChart.invalidate(); // refresh
    }
    /**
     * Creates graph, adds data
     */
    private void setupHeartGraph() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(00, 52));
        entries.add(new Entry(01, 92));
        entries.add(new Entry(02, 149));
        entries.add(new Entry(03, 192));
        entries.add(new Entry(04, 89));
        entries.add(new Entry(05, 98));
        entries.add(new Entry(06, 102));
        entries.add(new Entry(07, 120));

        LineDataSet heartSet = new LineDataSet(entries, "Pulse");
        heartSet.setLineWidth(10);
        LineData lineData = new LineData(heartSet);
        heartChart.setData(lineData);
    }

    /**
     * Sets up the gradient, colors, etc.
     */
    private void setupGraphStyle() {

        heartChart = (LineChart) findViewById(R.id.graph_heart);
        int graphHeight = heartChart.getHeight();
        Paint paint = heartChart.getRenderer().getPaintRender();
        LinearGradient linGrad = new LinearGradient(0, 0, 0, graphHeight,
                getResources().getColor(R.color.colorCardHeader),
                getResources().getColor(R.color.colorCardHeader2),
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);
    }


    public void dateChange(View view) {
        if (view == findViewById(R.id.dateBackButton)) {

        }
    }
}


























