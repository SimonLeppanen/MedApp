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
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.simon.medapp.R.color.accent_material_dark;
import static com.example.simon.medapp.R.color.highlighter1;
import static java.lang.Math.abs;


/**
 * Created by simon on 2017-04-17.
 */


public class HeartActivity extends AppCompatActivity {

    private List<Entry> entries1;
    private List<Entry> entries2;
    private LineChart heartChart;
    private Calendar activeDate;
    private Calendar today;
    LineDataSet heartSet1;
    LineDataSet heartSet2;
    LineData lineData1;
    LineData lineData2;

    int peakColor;
    int cardioColor;
    int fatburnColor;
    int restColor;

    private double minValue;
    private double maxValue;

    private double minPulse = 0;
    private double maxPulse;
    private int pulse;

    private double fatburnLimit;
    private double cardioLimit;
    private double peakLimit;

    private TextView pulseNbr;
    private TextView pulseText;
    private TextView heartZone;
    private TextView restNbr;
    private TextView hrvNbr;
    private ImageView heartIcon;

    private int currentPulse;
    private int origTextColor;

    private boolean firstSet;
    private int pulseOrig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));
        TableLayout t = (TableLayout) findViewById(R.id.heart_table);
        t.setVisibility(View.INVISIBLE);

        //Runs graph to be able to create layout
        findViewById(R.id.graph_heart).post(new Runnable() {
            @Override
            public void run() {
                drawGraph();

            }
        });

        pulse = 67;
        pulseOrig = 67;
        restNbr = (TextView) findViewById(R.id.rest_nbr);
        hrvNbr = (TextView) findViewById(R.id.hrv_nbr);
        pulseNbr = (TextView) findViewById(R.id.pulse_nbr);
        pulseText = (TextView) findViewById(R.id.pulse_txt);
        heartZone = (TextView) findViewById(R.id.heartZone);
        heartIcon = (ImageView) findViewById(R.id.heart_icon);
        firstSet = true;
        activeDate = Calendar.getInstance();
        today = Calendar.getInstance();

        setDateInActivity(today);
        drawGraph();

    }

    private void grayedOut(boolean b) {
        if (b) {
            pulseNbr.setAlpha(.33f);
            pulseText.setAlpha(.33f);
            heartZone.setAlpha(.33f);
            heartIcon.setAlpha(.33f);

        }
        if (!b) {
            pulseNbr.setAlpha(1f);
            pulseText.setAlpha(1f);
            heartZone.setAlpha(1f);
            heartIcon.setAlpha(1f);
        }

    }
    private void setDateInActivity(Calendar calendar) {
        // NOT FINISHED!
        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(Methods.getDay(calendar));
        dateBox.setText(Methods.getDate(calendar));
        if (!DateUtils.isToday(calendar.getTimeInMillis())) {
            grayedOut(true);
        }
        if (DateUtils.isToday(calendar.getTimeInMillis())) {
            grayedOut(false);
        }
    }

    public void drawGraph() {
        setupEntries();
        setupGraph();
        setupGradient();

        heartChart.invalidate(); // refresh
    }

    private void setupEntries() {
        entries1 = new ArrayList<>();
        entries1.add(new Entry(0, 46));
        entries1.add(new Entry(1, 92));
        entries1.add(new Entry(2, 149));
        entries1.add(new Entry(3, 187));
        entries1.add(new Entry(4, 89));
        entries1.add(new Entry(5, 98));
        entries1.add(new Entry(6, 102));
        entries1.add(new Entry(7, 120));
        entries1.add(new Entry(8, 46));
        entries1.add(new Entry(9, 92));
        entries1.add(new Entry(10, 102));
        entries1.add(new Entry(11, 121));
        entries1.add(new Entry(12, 142));
        entries1.add(new Entry(13, 157));
        entries1.add(new Entry(14, 175));
        entries1.add(new Entry(15, 143));
        entries1.add(new Entry(16, 102));
        entries1.add(new Entry(17, 74));
        entries1.add(new Entry(18, 65));
        entries1.add(new Entry(19, 54));
        entries1.add(new Entry(20, 67));
        entries1.add(new Entry(21, 76));
        entries1.add(new Entry(22, 178));
        entries1.add(new Entry(23, 68));
        entries1.add(new Entry(24, 72));

        entries2 = new ArrayList<>();
        entries2.add(new Entry(0, 56));
        entries2.add(new Entry(1, 91));
        entries2.add(new Entry(2, 129));
        entries2.add(new Entry(3, 167));
        entries2.add(new Entry(4, 84));
        entries2.add(new Entry(5, 93));
        entries2.add(new Entry(6, 132));
        entries2.add(new Entry(7, 140));
        entries2.add(new Entry(8, 146));
        entries2.add(new Entry(9, 152));
        entries2.add(new Entry(10, 142));
        entries2.add(new Entry(11, 121));
        entries2.add(new Entry(12, 142));
        entries2.add(new Entry(13, 137));
        entries2.add(new Entry(14, 165));
        entries2.add(new Entry(15, 153));
        entries2.add(new Entry(16, 132));
        entries2.add(new Entry(17, 75));
        entries2.add(new Entry(18, 63));
        entries2.add(new Entry(19, 55));
        entries2.add(new Entry(20, 68));
        entries2.add(new Entry(21, 75));
        entries2.add(new Entry(22, 148));
        entries2.add(new Entry(23, 65));
        entries2.add(new Entry(24, 74));

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

        peakColor = ContextCompat.getColor(this,R.color.heart_peak);
        cardioColor = ContextCompat.getColor(this,R.color.heart_cardio);
        fatburnColor = ContextCompat.getColor(this,R.color.heart_fatburn);
        restColor = ContextCompat.getColor(this,R.color.heart_rest);
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
        heartSet1 = new LineDataSet(entries1, "Pulse");
        heartSet2 = new LineDataSet(entries2, "Pulse");
        lineData1 = new LineData(heartSet1);
        lineData2 = new LineData(heartSet2);
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
        xAxis.setValueFormatter(new XAxisValueFormatter());


        //Highlighter&listener
        heartSet1.setHighLightColor(ContextCompat.getColor(this, highlighter1));
        heartSet1.setHighlightLineWidth(3f);
        heartSet1.setDrawHorizontalHighlightIndicator(false);

        heartSet2.setHighLightColor(ContextCompat.getColor(this, highlighter1));
        heartSet2.setHighlightLineWidth(3f);
        heartSet2.setDrawHorizontalHighlightIndicator(false);

        heartChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                    int highlighter1 = getResources().getColor(R.color.highlighter1);
                    heartZone = (TextView) findViewById(R.id.heartZone);
                    pulse = (int) h.getY();

                    if (pulse < fatburnLimit) {
                        heartZone.setText("RESTING");
                    } else if (pulse >= fatburnLimit && pulse < cardioLimit) {
                        heartZone.setText("FATBURN");
                    } else if (pulse >= cardioLimit && pulse < peakLimit) {
                        heartZone.setText("CARDIO");
                    } else if (pulse >= peakLimit) {
                        heartZone.setText("PEAK");
                    }

                    grayedOut(false);
                    pulseNbr = (TextView) findViewById(R.id.pulse_nbr);
                    pulseNbr.setText(Integer.toString(pulse));
                    pulseNbr.setTextColor(highlighter1);

                    pulseText = (TextView) findViewById(R.id.pulse_txt);
                    pulseText.setText("CURSOR");
                    pulseText.setTextColor(highlighter1);

            }

            @Override
            public void onNothingSelected() {
                int default_gray = getResources().getColor(R.color.default_gray);
                if (!DateUtils.isToday(activeDate.getTimeInMillis())) {
                    grayedOut(true);
                    pulseNbr.setText(Integer.toString(pulse));
                    pulseNbr.setTextColor(default_gray);
                    pulseText.setTextColor(default_gray);
                }
                if (DateUtils.isToday(activeDate.getTimeInMillis())) {
                    pulseNbr.setText(Integer.toString(pulseOrig));
                    pulseNbr.setTextColor(default_gray);
                    pulseText.setTextColor(default_gray);
                    pulseText.setText("PULSE");
                }
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
        heartSet1.setLineWidth(5);
        heartSet1.setDrawCircles(false);
        heartSet1.setDrawValues(false);
        heartSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        heartSet2.setLineWidth(5);
        heartSet2.setDrawCircles(false);
        heartSet2.setDrawValues(false);
        heartSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Initiate graph
        heartChart.setData(lineData1);
    }

    private void switchSet() {
        if (firstSet) {
            firstSet = false;
            heartChart.setData(lineData2);
            heartChart.invalidate();
            restNbr.setText("54");
            hrvNbr.setText("52");
        } else {
            firstSet = true;
            heartChart.setData(lineData1);
            heartChart.invalidate();
            restNbr.setText("52");
            hrvNbr.setText("50");
        }

    }

    public void dateChange(View view) {

        if (view == findViewById(R.id.heart_dateBackButton)) {

            activeDate.add(Calendar.DATE,-1);
            setDateInActivity(activeDate);
            switchSet();
        }
        if (view == findViewById(R.id.heart_dateForwardButton)) {
            if (!DateUtils.isToday(activeDate.getTimeInMillis())) {
                activeDate.add(Calendar.DATE, +1);
                setDateInActivity(activeDate);
                switchSet();
            }
        }
        pulseNbrClicked(new View(this));

    }

    public void pulseNbrClicked(View view) {
        heartChart.highlightValue(null,true);
/*
        int default_gray = getResources().getColor(R.color.default_gray);

        pulseNbr.setTextColor(default_gray);
        pulseNbr.setText(Integer.toString(pulse));

        pulseText.setTextColor(default_gray);
        pulseText.setText("PULSE");*/
    }
}

class XAxisValueFormatter implements IAxisValueFormatter {

    DecimalFormat d = new DecimalFormat("00");
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return d.format((int) value) + ":00";
    }
}

























