package com.example.simon.medapp;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by simon on 2017-04-18.
 */

public class TemperatureActivity extends AppCompatActivity {

    float maxTemp = 42.5f;
    float fever = 37.5f;
    float minTemp = 32.5f;
    private List <Entry> entries1;
    private List <Entry> entries2;

    private int feverColor;
    private int normalColor;

    private String cursorTemp;


    private LineChart temperatureChart;

    private TextView temperatureNbr;
    private TextView temperatureText;
    private TextView temperatureC;

    private String temperatureNbrOrig = "36,8";
    private String temperatureTextOrig = "NORMAL";
    private Calendar activeDate;
    private Calendar today;
    private LineData lineData1;
    private LineData lineData2;

    private boolean firstSetActive;
    private ImageView temperatureImg;
    private TextView tempTime;
    private ImageView rightDateArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        findViewById(R.id.graph_temperature).post(new Runnable() {
            @Override
            public void run() {
                drawGraph();

            }
        });


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));

        rightDateArrow = (ImageView) findViewById(R.id.temp_dateForwardButton);
        activeDate = Calendar.getInstance();
        today = Calendar.getInstance();
        firstSetActive = true;
        cursorTemp = "36,8";
        temperatureImg = (ImageView) findViewById(R.id.temp_img);
        tempTime = (TextView) findViewById(R.id.temp_time);

        getTextViews();
        setDateInActivity(today);
        drawGraph();


    }

    public void getTextViews() {
        temperatureNbr = (TextView) findViewById(R.id.temp_nbr);
        temperatureC = (TextView) findViewById(R.id.temp_c);
        temperatureText = (TextView) findViewById(R.id.temp_text);


    }

    private void setDateInActivity(Calendar calendar) {

        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(Methods.getDay(calendar));
        dateBox.setText(Methods.getDate(calendar));
        if (temperatureChart != null) {
            temperatureChart.highlightValue(null,true);
        }
        if (DateUtils.isToday(activeDate.getTimeInMillis())) {
            temperatureNbr.setText(temperatureNbrOrig);
            rightDateArrow.setAlpha(.33f);

            greyedOut(false);
        }
        if (!DateUtils.isToday(activeDate.getTimeInMillis())) {
            rightDateArrow.setAlpha(1f);
            greyedOut(true);
        }
    }

    private void greyedOut(boolean b) {
        if (b) {
            temperatureNbr.setAlpha(.33f);
            temperatureText.setAlpha(.33f);
            temperatureC.setAlpha(.33f);
            temperatureImg.setAlpha(.33f);
        }
        if (!b) {
            temperatureNbr.setAlpha(1f);
            temperatureText.setAlpha(1f);
            temperatureC.setAlpha(1f);
            temperatureImg.setAlpha(1f);


        }
    }

    public void dateChange(View view) {

        if (view == findViewById(R.id.temp_dateBackButton)) {
            activeDate.add(Calendar.DATE,-1);
            setDateInActivity(activeDate);
            switchSet();
        }
        if (view == findViewById(R.id.temp_dateForwardButton)) {
            if (!DateUtils.isToday(activeDate.getTimeInMillis())) {
                activeDate.add(Calendar.DATE, +1);
                setDateInActivity(activeDate);
                switchSet();
            }
        }

    }

    private void switchSet() {
        if (firstSetActive) {
            firstSetActive = false;
            temperatureChart.setData(lineData2);
            temperatureChart.invalidate();
        } else {
            firstSetActive = true;
            temperatureChart.setData(lineData1);
            temperatureChart.invalidate();
        }
    }

    public void drawGraph() {
        setupEntries();
        setupGraph();
        setupGradient();

        temperatureChart.invalidate(); // refresh
    }

    private void setupEntries() {
        entries1 = new ArrayList<>();
        entries1.add(new Entry(0, 36.1f));
        entries1.add(new Entry(1, 36.5f));
        entries1.add(new Entry(2, 36.9f));
        entries1.add(new Entry(3, 37.6f));
        entries1.add(new Entry(4, 38.3f));
        entries1.add(new Entry(5, 37.4f));
        entries1.add(new Entry(6, 37.1f));
        entries1.add(new Entry(7, 36.6f));
        entries1.add(new Entry(8, 36.2f));
        entries1.add(new Entry(9, 36.1f));
        entries1.add(new Entry(10, 35.8f));
        entries1.add(new Entry(11, 36.6f));
        entries1.add(new Entry(12, 36.4f));
        entries1.add(new Entry(13, 36.9f));
        entries1.add(new Entry(14, 37.1f));
        entries1.add(new Entry(15, 37.3f));
        entries1.add(new Entry(16, 37.5f));
        entries1.add(new Entry(17, 37.6f));
        entries1.add(new Entry(18, 38.6f));
        entries1.add(new Entry(19, 38.9f));
        entries1.add(new Entry(20, 39.1f));
        entries1.add(new Entry(21, 38.6f));
        entries1.add(new Entry(22, 36.9f));
        entries1.add(new Entry(23, 35.9f));
        entries1.add(new Entry(24, 35.5f));

        entries2 = new ArrayList<>();
        entries2.add(new Entry(0, 36.3f));
        entries2.add(new Entry(1, 36.5f));
        entries2.add(new Entry(2, 36.7f));
        entries2.add(new Entry(3, 37.0f));
        entries2.add(new Entry(4, 38.1f));
        entries2.add(new Entry(5, 37.8f));
        entries2.add(new Entry(6, 36.1f));
        entries2.add(new Entry(7, 36.6f));
        entries2.add(new Entry(8, 35.2f));
        entries2.add(new Entry(9, 35.1f));
        entries2.add(new Entry(10, 36.8f));
        entries2.add(new Entry(11, 36.6f));
        entries2.add(new Entry(12, 36.4f));
        entries2.add(new Entry(13, 37.9f));
        entries2.add(new Entry(14, 37.1f));
        entries2.add(new Entry(15, 38.3f));
        entries2.add(new Entry(16, 39.5f));
        entries2.add(new Entry(17, 40.6f));
        entries2.add(new Entry(18, 39.6f));
        entries2.add(new Entry(19, 38.9f));
        entries2.add(new Entry(20, 37.1f));
        entries2.add(new Entry(21, 36.6f));
        entries2.add(new Entry(22, 36.9f));
        entries2.add(new Entry(23, 37.9f));
        entries2.add(new Entry(24, 37.8f));

    }


    /**
     * Sets up the gradient, colors, etc.
     */
    private void setupGradient() {

        ViewPortHandler v = temperatureChart.getViewPortHandler();
        float chartHeight = v.contentHeight();
        float viewHeight = v.getChartHeight();
        float y0 = v.contentTop();
        float y1 = v.contentBottom();


        int graphHeight = temperatureChart.getHeight();
        float grad = .01f;

        normalColor = ContextCompat.getColor(this,R.color.temperature_normal);
        feverColor = ContextCompat.getColor(this,R.color.temperature_fever);
        int[] colors = {feverColor,feverColor,normalColor,normalColor};

        float[] positions = {0f,.5f-grad,.5f+grad,1f};

        LinearGradient linGrad = new LinearGradient(0, y0, 0, y1,
                colors,
                positions,
                Shader.TileMode.REPEAT);

        Paint paint = temperatureChart.getRenderer().getPaintRender();
        paint.setShader(linGrad);
    }

    /**
     * Creates graph, adds data
     */

    private void setupGraph() {
        LineDataSet temperatureSet1 = new LineDataSet(entries1, "Pulse");
        LineDataSet temperatureSet2 = new LineDataSet(entries2, "Pulse");

        temperatureChart = (LineChart) findViewById(R.id.graph_temperature);
        temperatureSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        temperatureSet2.setAxisDependency(YAxis.AxisDependency.LEFT);



        // Set style on graph bg
        temperatureChart.getAxisLeft().setDrawGridLines(false);
        temperatureChart.getAxisRight().setDrawGridLines(false);
        temperatureChart.getXAxis().setDrawGridLines(false);
        temperatureChart.setDrawGridBackground(false);
        temperatureChart.setDrawBorders(false);
        Description description = new Description();
        description.setText("");
        temperatureChart.setDescription(description);
        Legend legend = temperatureChart.getLegend();
        legend.setEnabled(false);

        // Y-axis
        YAxis yAxisLeft = temperatureChart.getAxisLeft();
        YAxis yAxisRight = temperatureChart.getAxisRight();
        yAxisLeft.setAxisMinimum(32.5f);
        yAxisLeft.setAxisMaximum(42.5f);
        yAxisRight.setDrawLabels(false);
        //yAxisRight.setValueFormatter(new MyYAxisValueFormatter());
        //yAxisRight.setAxisMinimum(32.5f);
        //yAxisRight.setAxisMaximum(42.5f);
        //yAxisRight.setLabelCount(5, true);
        //yAxisRight.setGranularity(1f);


        // X-axis
        XAxis xAxis = temperatureChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XAxisValueFormatter());
        temperatureSet1.setLineWidth(10);
        temperatureSet2.setLineWidth(10);

        lineData1 = new LineData(temperatureSet1);
        lineData2 = new LineData(temperatureSet2);

        temperatureChart.setData(lineData1);

        //Highlighter
        temperatureSet1.setHighLightColor(ContextCompat.getColor(this,R.color.highlighter1));
        temperatureSet1.setHighlightLineWidth(3f);
        temperatureSet1.setDrawHorizontalHighlightIndicator(false);

        temperatureSet2.setHighLightColor(ContextCompat.getColor(this,R.color.highlighter1));
        temperatureSet2.setHighlightLineWidth(3f);
        temperatureSet2.setDrawHorizontalHighlightIndicator(false);

        temperatureChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int colorHighlight = getResources().getColor(R.color.highlighter1);
                int colorDefault = getResources().getColor(R.color.default_gray);
                int colorFever = getResources().getColor(R.color.temperature_fever);
                cursorTemp = Float.toString(h.getY());
                tempTime.setVisibility(View.VISIBLE);
                tempTime.setText(Methods.timeGetter(h.getX()));
                greyedOut(false);

                if (!isFever(e)) {
                    temperatureNbr.setText(Float.toString(e.getY()));
                    temperatureNbr.setTextColor(colorHighlight);
                    temperatureC.setTextColor(colorHighlight);
                    temperatureText.setText(temperatureTextOrig);
                    temperatureText.setTextColor(colorDefault);
                    temperatureText.setTypeface(null, Typeface.NORMAL);
                    temperatureImg.setImageResource(R.drawable.temperature_icon_2_normal);
                }

                if (isFever(e)) {
                    temperatureNbr.setText(Float.toString(e.getY()));
                    temperatureNbr.setTextColor(colorHighlight);
                    temperatureC.setTextColor(colorHighlight);
                    temperatureText.setText("FEVER");
                    temperatureText.setTypeface(null, Typeface.BOLD);
                    temperatureText.setTextColor(colorFever);
                    temperatureImg.setImageResource(R.drawable.temperature_icon_2_fever);

                }
            }

            @Override
            public void onNothingSelected() {
                tempTime.setVisibility(View.INVISIBLE);
                if (DateUtils.isToday(activeDate.getTimeInMillis())) {
                    greyedOut(false);
                    cursorTemp = temperatureNbrOrig;
                    temperatureImg.setImageResource(R.drawable.temperature_icon_2_normal);
                    temperatureText.setText(temperatureTextOrig);
                    temperatureText.setTypeface(null, Typeface.NORMAL);
                } else {
                    greyedOut(true);
                }
                int colorDefault = getResources().getColor(R.color.default_gray);

                temperatureText.setTextColor(colorDefault);

                temperatureNbr.setText(cursorTemp);
                temperatureNbr.setTextColor(colorDefault);
                temperatureC.setTextColor(colorDefault);

            }
        });


        // Zoom disabled
        temperatureChart.setScaleEnabled(false);

        // Set dashed lines
        LimitLine line1 = new LimitLine(fever,Float.toString(fever));
        line1.enableDashedLine(20f,20f,0f);
        line1.setLineColor(ContextCompat.getColor(this,R.color.default_gray));
        line1.setTextColor(feverColor);
        yAxisLeft.addLimitLine(line1);

        // Right labels
        LimitLine normalText = new LimitLine(fever-2f,"Normal");
        normalText.setLineColor(ContextCompat.getColor(this,R.color.transparent));
        normalText.setTextColor(normalColor);
        yAxisLeft.addLimitLine(normalText);

        LimitLine feverText = new LimitLine(fever+2f,"Fever");
        feverText.setLineColor(ContextCompat.getColor(this,R.color.transparent));
        feverText.setTextColor(feverColor);
        yAxisLeft.addLimitLine(feverText);

        // Style of line
        temperatureSet1.setLineWidth(5);
        temperatureSet1.setDrawCircles(false);
        temperatureSet1.setDrawValues(false);
        temperatureSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        temperatureSet2.setLineWidth(5);
        temperatureSet2.setDrawCircles(false);
        temperatureSet2.setDrawValues(false);
        temperatureSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Initiate graph
        temperatureChart.setData(lineData1);
    }

    public boolean isFever(Entry e) {
        return e.getY() > fever;
    }

    public void tempNbrClicked(View view) {
        temperatureChart.highlightValue(null,true);

    }
}







