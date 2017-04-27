package com.example.simon.medapp;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;


/**
 * Created by simon on 2017-04-17.
 */


public class HeartActivity extends AppCompatActivity {

    private List<Entry> entries;
    private LineChart heartChart;
    private Date date;
    private Calendar calendar;

    private double minValue;
    private double maxValue;

    private double minPulse = 0;
    private double maxPulse;

    private double fatburnLimit;
    private double cardioLimit;
    private double peakLimit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));

        //Runs graph to be able to create layout
        findViewById(R.id.graph_heart).post(new Runnable() {
            @Override
            public void run() {
                drawGraph();

            }
        });

        setTodaysDate();
        drawGraph();

    }

    private void setTodaysDate() {
        // NOT FINISHED!
        date = new Date();
        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(Methods.getDay(date));
        dateBox.setText(Methods.getDate(date));
    }

    public void drawGraph() {
        setupEntries();
        setupGraph();
        setupGradient();

        heartChart.invalidate(); // refresh
    }

    private void setupEntries() {
        entries = new ArrayList<>();
        entries.add(new Entry(00, 46));
        entries.add(new Entry(01, 92));
        entries.add(new Entry(02, 149));
        entries.add(new Entry(03, 187));
        entries.add(new Entry(04, 89));
        entries.add(new Entry(05, 98));
        entries.add(new Entry(06, 102));
        entries.add(new Entry(07, 120));

        minValue = 46;
        maxValue = 172;

    }

    /**
     * Sets up the gradient, colors, etc.
     */
    private void setupGradient() {

        //float graphLimitFatburn = (float) abs((fatburnLimit / (maxValue - minValue)));
        //float graphLimitCardio = (float) abs((cardioLimit / (maxValue - minValue))-1);
        //float graphLimitPeak = (float) abs((peakLimit / (maxValue - minValue))-1);


        ViewPortHandler v = heartChart.getViewPortHandler();
        float chartHeight = v.contentHeight();
        float viewHeight = v.getChartHeight();
        float y0 = v.contentTop();
        float y1 = v.contentBottom();

        int peakColor = ContextCompat.getColor(this,R.color.heart_peak);
        int cardioColor = ContextCompat.getColor(this,R.color.heart_cardio);
        int fatburnColor = ContextCompat.getColor(this,R.color.heart_fatburn);
        int restColor = ContextCompat.getColor(this,R.color.heart_rest);
        float margin = 0;




        Log.d("Top: ", Float.toString(v.contentTop()));
        Log.d("Bottom: ", Float.toString(v.contentBottom()));
        Log.d("viewHeight: : ", Float.toString(viewHeight));
        Log.d("chartHeight: ", Float.toString(chartHeight));


        int[] gradColors = {peakColor, peakColor,cardioColor,cardioColor,fatburnColor,fatburnColor,restColor,restColor};
        float[] positions = {0f,.15f,.15f,.3f,.3f,.5f,.5f,1f};
        Paint paint = heartChart.getRenderer().getPaintRender();
        LinearGradient linGrad = new LinearGradient(0f, y0, 0f, y1,
                gradColors,
                positions,
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);
        heartChart.invalidate();
    }

    /**
     * Creates graph, adds data
     */
    private void setupGraph() {
        int age = 24;
        maxPulse = 220 - age;
        fatburnLimit = .5 * maxPulse;
        cardioLimit = .7 * maxPulse;
        peakLimit = .85 * maxPulse;

        // Setup DataSet, LineData, LineChart
        LineDataSet heartSet = new LineDataSet(entries, "Pulse");
        LineData lineData = new LineData(heartSet);
        heartChart = (LineChart) findViewById(R.id.graph_heart);

        // Set style on graph bg
        heartChart.getAxisLeft().setDrawGridLines(false);
        heartChart.getAxisRight().setDrawGridLines(false);
        heartChart.getXAxis().setDrawGridLines(false);
        heartChart.setDrawGridBackground(false);
        heartChart.setDrawBorders(false);
        Description description = new Description();
        description.setText("");
        heartChart.setDescription(description);
        Legend legend = heartChart.getLegend();
        legend.setEnabled(false);

        // Y-axis
        YAxis yAxisLeft = heartChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum((float) maxPulse);

        // X-axis
        XAxis xAxis = heartChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //Highlighter
        heartSet.setHighLightColor(ContextCompat.getColor(this,R.color.highlighter));

        // Zoom disabled
        heartChart.setScaleEnabled(false);

        // Set dashed lines
        LimitLine line1 = new LimitLine((float) fatburnLimit);
        line1.enableDashedLine(20f,20f,0f);
        line1.setLineColor(ContextCompat.getColor(this,R.color.default_gray));
        yAxisLeft.addLimitLine(line1);

        LimitLine line2 = new LimitLine((float) cardioLimit);
        line2.enableDashedLine(20f,20f,0f);
        line2.setLineColor(ContextCompat.getColor(this,R.color.default_gray));
        yAxisLeft.addLimitLine(line2);

        LimitLine line3 = new LimitLine((float) peakLimit);
        line3.enableDashedLine(20f,20f,0f);
        line3.setLineColor(ContextCompat.getColor(this,R.color.default_gray));
        yAxisLeft.addLimitLine(line3);

        // Style of line
        heartSet.setLineWidth(5);
        heartSet.setDrawCircles(false);
        heartSet.setDrawValues(false);
        heartSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Initiate graph
        heartChart.setData(lineData);
    }
}


























