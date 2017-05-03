package com.example.simon.medapp;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TableLayout;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.textStyle;
import static android.graphics.Typeface.BOLD;
import static com.example.simon.medapp.R.color.default_gray;
import static com.example.simon.medapp.R.color.highlighter1;
import static com.example.simon.medapp.R.color.highlighter2;
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

    private TextView pulseNbr;
    private TextView pulseText;
    private int currentPulse;
    private int origTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));
        TableLayout t = (TableLayout) findViewById(R.id.heart_table);
        t.setVisibility(View.INVISIBLE);
        currentPulse = 67;
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
        entries.add(new Entry(0, 46));
        entries.add(new Entry(1, 92));
        entries.add(new Entry(2, 149));
        entries.add(new Entry(3, 187));
        entries.add(new Entry(4, 89));
        entries.add(new Entry(5, 98));
        entries.add(new Entry(6, 102));
        entries.add(new Entry(7, 120));
        entries.add(new Entry(8, 46));
        entries.add(new Entry(9, 92));
        entries.add(new Entry(10, 102));
        entries.add(new Entry(11, 121));
        entries.add(new Entry(12, 142));
        entries.add(new Entry(13, 157));
        entries.add(new Entry(14, 175));
        entries.add(new Entry(15, 143));
        entries.add(new Entry(16, 102));
        entries.add(new Entry(17, 74));
        entries.add(new Entry(18, 65));
        entries.add(new Entry(19, 54));
        entries.add(new Entry(20, 67));
        entries.add(new Entry(21, 76));
        entries.add(new Entry(22, 178));
        entries.add(new Entry(23, 68));
        entries.add(new Entry(24, 72));



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
        float margin = .075f;

        int[] gradColors = {peakColor, peakColor,cardioColor,cardioColor,fatburnColor,fatburnColor,restColor,restColor};
        float[] positions = {0f,.15f-margin,.15f+margin,.3f-margin,.3f+margin,.5f-margin,.5f+margin,1f};
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

        YAxis yAxisRight = heartChart.getAxisRight();
        yAxisRight.setDrawLabels(false);

        // X-axis
        XAxis xAxis = heartChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //Highlighter&listener
        heartSet.setHighLightColor(ContextCompat.getColor(this, highlighter1));
        heartSet.setHighlightLineWidth(3f);
        heartSet.setDrawHorizontalHighlightIndicator(false);
        heartChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int highlighter1 = getResources().getColor(R.color.highlighter1);
                pulseNbr = (TextView) findViewById(R.id.pulse_nbr);
                pulseNbr.setText(Integer.toString(Math.round(e.getY())));
                pulseNbr.setTextColor(highlighter1);

                pulseText = (TextView) findViewById(R.id.pulse_txt);
                pulseText.setText("CURSOR");
                pulseText.setTextColor(highlighter1);

            }

            @Override
            public void onNothingSelected() {
                pulseNbr = (TextView) findViewById(R.id.pulse_nbr);
                pulseText = (TextView) findViewById(R.id.pulse_txt);

                int default_gray = getResources().getColor(R.color.default_gray);

                pulseNbr.setTextColor(default_gray);
                pulseNbr.setText(Integer.toString(currentPulse));

                pulseText.setTextColor(default_gray);
                pulseText.setText("PULSE");

            }
        });

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


        float text1pos = (float) (fatburnLimit/2)-5;
        LimitLine text1 = new LimitLine(text1pos,"Rest");
        text1.setTextColor(ContextCompat.getColor(this,R.color.heart_rest));
        text1.setTextSize(14f);
        text1.setLineColor(ContextCompat.getColor(this,R.color.transparent));
        yAxisLeft.addLimitLine(text1);

        float text2pos = (float) (((cardioLimit-fatburnLimit)/2)+fatburnLimit-5);
        LimitLine text2 = new LimitLine(text2pos,"Fatburn");
        text2.setTextColor(ContextCompat.getColor(this,R.color.heart_fatburn));
        text2.setTextSize(14f);
        text2.setLineColor(ContextCompat.getColor(this,R.color.transparent));
        yAxisLeft.addLimitLine(text2);

        float text3pos = (float) (((peakLimit-cardioLimit)/2)+cardioLimit-5);
        LimitLine text3 = new LimitLine(text3pos,"Cardio");
        text3.setTextColor(ContextCompat.getColor(this,R.color.heart_cardio));
        text3.setTextSize(14f);
        text3.setLineColor(ContextCompat.getColor(this,R.color.transparent));
        text3.setTextStyle(Paint.Style.FILL);
        yAxisLeft.addLimitLine(text3);

        float text4pos = (float) (((maxPulse-peakLimit)/2)+peakLimit-5);
        LimitLine text4 = new LimitLine(text4pos,"Peak");
        text4.setTextColor(ContextCompat.getColor(this,R.color.heart_peak));
        text4.setTextSize(14f);
        text4.setLineColor(ContextCompat.getColor(this,R.color.transparent));
        yAxisLeft.addLimitLine(text4);


        // Style of line
        heartSet.setLineWidth(5);
        heartSet.setDrawCircles(false);
        heartSet.setDrawValues(false);
        heartSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Initiate graph
        heartChart.setData(lineData);
    }
}


























