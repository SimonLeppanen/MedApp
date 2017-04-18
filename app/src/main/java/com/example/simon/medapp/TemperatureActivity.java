package com.example.simon.medapp;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 2017-04-18.
 */

public class TemperatureActivity extends AppCompatActivity {

    private Date date;
    private LineChart temperatureChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        setTodaysDate();
        drawTemperatureGraph();

    }

    private void setTodaysDate() {
        // NOT FINISHED!
        date = new Date();
        TextView dayBox = (TextView) findViewById(R.id.dayBox);
        TextView dateBox = (TextView) findViewById(R.id.dateBox);
        dayBox.setText(getDay(date));
        dateBox.setText(getDate(date));
    }

    public void drawTemperatureGraph() {
        setupGraphStyle();
        setupTemperatureGraph();
        temperatureChart.invalidate(); // refresh
    }

    /**
     * Sets up the gradient, colors, etc.
     */
    private void setupGraphStyle() {

        temperatureChart = (LineChart) findViewById(R.id.graph_heart);
        int graphHeight = temperatureChart.getHeight();
        Paint paint = temperatureChart.getRenderer().getPaintRender();
        LinearGradient linGrad = new LinearGradient(0, 0, 0, graphHeight,
                getResources().getColor(R.color.colorCardHeader),
                getResources().getColor(R.color.colorCardHeader2),
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);
    }

    /**
     * Creates graph, adds data
     */
    private void setupTemperatureGraph() {
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
        temperatureChart.setData(lineData);
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


