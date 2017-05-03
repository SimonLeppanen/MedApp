package com.example.simon.medapp;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 2017-04-18.
 */

public class TemperatureActivity extends AppCompatActivity {

    float maxTemp = 42.5f;
    float fever = 37.5f;
    float minTemp = 32.5f;
    private List <Entry> entries;
    private int feverColor;
    private int normalColor;

    private Date date;
    private LineChart temperatureChart;

    private TextView temperatureNbr;
    private TextView temperatureText;
    private TextView temperatureC;

    private String temperatureNbrOrig = "36,8";
    private String temperatureTextOrig = "NORMAL";


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

        setTodaysDate();
        getTextViews();
        drawGraph();


    }

    public void getTextViews() {
        temperatureNbr = (TextView) findViewById(R.id.temp_nbr);
        temperatureC = (TextView) findViewById(R.id.temp_c);
        temperatureText = (TextView) findViewById(R.id.temp_text);


    }

    private void setTodaysDate() {
        // NOT FINISHED!
        date = new Date();
        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(getDay(date));
        dateBox.setText(getDate(date));
    }

    public void drawGraph() {
        setupEntries();
        setupGraph();
        setupGradient();

        temperatureChart.invalidate(); // refresh
    }

    private void setupEntries() {
        entries = new ArrayList<>();
        entries.add(new Entry(0, 36.1f));
        entries.add(new Entry(1, 36.5f));
        entries.add(new Entry(2, 36.9f));
        entries.add(new Entry(3, 37.6f));
        entries.add(new Entry(4, 38.3f));
        entries.add(new Entry(5, 37.4f));
        entries.add(new Entry(6, 37.1f));
        entries.add(new Entry(7, 36.6f));
        entries.add(new Entry(8, 36.2f));
        entries.add(new Entry(9, 36.1f));
        entries.add(new Entry(10, 35.8f));
        entries.add(new Entry(11, 36.6f));
        entries.add(new Entry(12, 36.4f));
        entries.add(new Entry(13, 36.9f));
        entries.add(new Entry(14, 37.1f));
        entries.add(new Entry(15, 37.3f));
        entries.add(new Entry(16, 37.5f));
        entries.add(new Entry(17, 37.6f));
        entries.add(new Entry(18, 38.6f));
        entries.add(new Entry(19, 38.9f));
        entries.add(new Entry(20, 39.1f));
        entries.add(new Entry(21, 38.6f));
        entries.add(new Entry(22, 36.9f));
        entries.add(new Entry(23, 35.9f));
        entries.add(new Entry(24, 35.5f));


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
        LineDataSet temperatureSet = new LineDataSet(entries, "Pulse");
        temperatureChart = (LineChart) findViewById(R.id.graph_temperature);
        temperatureSet.setAxisDependency(YAxis.AxisDependency.LEFT);



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
        temperatureSet.setLineWidth(10);
        LineData lineData = new LineData(temperatureSet);
        temperatureChart.setData(lineData);

        //Highlighter
        temperatureSet.setHighLightColor(ContextCompat.getColor(this,R.color.highlighter2));
        temperatureSet.setHighlightLineWidth(3f);
        temperatureSet.setDrawHorizontalHighlightIndicator(false);
        temperatureChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int colorHighlight = getResources().getColor(R.color.highlighter2);
                int colorDefault = getResources().getColor(R.color.default_gray);
                int colorFever = getResources().getColor(R.color.temperature_fever);
                if (!isFever(e)) {
                    temperatureNbr.setText(Float.toString(e.getY()));
                    temperatureNbr.setTextColor(colorHighlight);
                    temperatureC.setTextColor(colorHighlight);
                    temperatureText.setText(temperatureTextOrig);
                    temperatureText.setTextColor(colorDefault);
                }

                if (isFever(e)) {
                    temperatureNbr.setText(Float.toString(e.getY()));
                    temperatureNbr.setTextColor(colorHighlight);
                    temperatureC.setTextColor(colorHighlight);
                    temperatureText.setText("FEVER");
                    temperatureText.setTextColor(colorFever);

                }
            }

            @Override
            public void onNothingSelected() {
                int colorDefault = getResources().getColor(R.color.default_gray);
                temperatureText.setTextColor(colorDefault);
                temperatureText.setText(temperatureTextOrig);
                temperatureNbr.setText(temperatureNbrOrig);
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
        temperatureSet.setLineWidth(5);
        temperatureSet.setDrawCircles(false);
        temperatureSet.setDrawValues(false);
        temperatureSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Initiate graph
        temperatureChart.setData(lineData);
    }

    public boolean isFever(Entry e) {
        return e.getY() > fever;
    }

    /**
     * Changes date when arrows clicked in layout, NOT FINISHED
     * @param view id of arrow-button
     */
    public void dateChange(View view) {
        if (view == findViewById(R.id.dateBackButton)) {

        }
    }

    /**
     *
     * @param d: date-object
     * @return String for day of the week fully written out, ex. Friday
     */
    public String getDay(Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        return dateFormat.format(d);
    }

    /**
     *
     * @param d: date-object
     * @return String for month and day, ex. September, 25
     */
    public String getDate(Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, d");
        return dateFormat.format(d);
    }
}

class MyYAxisValueFormatter implements IAxisValueFormatter {

    boolean normal;
    boolean fever;

    public MyYAxisValueFormatter() {
        normal = false;
        fever = false;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value == 35f) {
            return "Normal";
        }
        if (value == 40f) {
            return "Fever";
        }
        return "";
    }
}





