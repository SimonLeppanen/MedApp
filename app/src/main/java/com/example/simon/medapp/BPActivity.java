package com.example.simon.medapp;

import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 2017-04-18.
 */

public class BPActivity extends AppCompatActivity {

    Date date;
    private LineChart bpLineChart;
    private BarChart bpBarChart;
    private List<Entry> entries_syst;
    private List<Entry> entries_diast;
    private LineDataSet lineSet_syst;
    private LineDataSet lineSet_diast;
    private List<BarEntry> entriesBar;
    private List<BarEntry> entriesBar_syst;
    private List<BarEntry> entriesBar_diast;
    private BarDataSet barDataSet;
    private BarDataSet barDataSet_syst;
    private BarDataSet barDataSet_diast;

    private String avgSystOrig;
    private String avgDiastOrig;
    private String nowSystOrig;
    private String nowDiastOrig;
    private String nowTextOrig;

    private TextView avgSyst;
    private TextView avgDiast;
    private TextView avgText;
    private TextView nowSyst;
    private TextView nowDiast;
    private TextView nowText;

    private Highlight lineHighlight;
    private boolean barSelected;
    private boolean lineHighlightActive;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader)));

        //Runs graph to be able to create layout
        findViewById(R.id.linegraph_bp).post(new Runnable() {
            @Override
            public void run() {
                setupLineEntries();
            }
        });
        findViewById(R.id.bargraph_bp).post(new Runnable() {
            @Override
            public void run() {
                setupBarEntries();
            }
        });

        getTextViews();
        setTodaysDate();
        drawGraphs();

    }

    private void getTextViews() {
        avgSystOrig = "135";
        avgDiastOrig = "72";
        nowSystOrig = "131";
        nowDiastOrig = "71";
        nowTextOrig = "NOW";
        barSelected = false;
        lineHighlightActive = false;

        avgSyst = (TextView) findViewById(R.id.right_syst_nbr);
        avgDiast = (TextView) findViewById(R.id.right_diast_nbr);
        avgText = (TextView) findViewById(R.id.bp_right_text);
        nowSyst = (TextView) findViewById(R.id.left_syst_nbr);
        nowDiast = (TextView) findViewById(R.id.left_diast_nbr);
        nowText = (TextView) findViewById(R.id.bp_left_text);

    }
    private void drawGraphs() {


        setupLineEntries();
        setupLineGraph();
        setupBarEntries();
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
    private void setupLineEntries() {

        entries_syst = new ArrayList<>();
        entries_syst.add(new Entry(00, 132));
        entries_syst.add(new Entry(01, 131));
        entries_syst.add(new Entry(02, 123));
        entries_syst.add(new Entry(03, 131));
        entries_syst.add(new Entry(04, 121));
        entries_syst.add(new Entry(05, 143));
        entries_syst.add(new Entry(06, 138));
        entries_syst.add(new Entry(07, 135));

        entries_diast = new ArrayList<>();
        entries_diast.add(new Entry(00, 71));
        entries_diast.add(new Entry(01, 77));
        entries_diast.add(new Entry(02, 67));
        entries_diast.add(new Entry(03, 81));
        entries_diast.add(new Entry(04, 71));
        entries_diast.add(new Entry(05, 74));
        entries_diast.add(new Entry(06, 78));
        entries_diast.add(new Entry(07, 72));
    }

    private void setupLineGraph() {

        bpLineChart = (LineChart) findViewById(R.id.linegraph_bp);

        lineSet_syst = new LineDataSet(entries_syst, "Systolic");
        lineSet_diast = new LineDataSet(entries_diast, "Diastolic");


        lineSet_syst.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineSet_diast.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineSet_syst);
        dataSets.add(lineSet_diast);

        LineData lineData = new LineData(dataSets);


        // Set style on graph bg
        bpLineChart.getAxisLeft().setDrawGridLines(false);
        bpLineChart.getAxisRight().setDrawGridLines(false);
        bpLineChart.getXAxis().setDrawGridLines(false);
        bpLineChart.setDrawGridBackground(false);
        bpLineChart.setDrawBorders(false);
        Description description = new Description();
        description.setText("");
        bpLineChart.setDescription(description);
        Legend legend = bpLineChart.getLegend();
        legend.setEnabled(false);

        // Y-axis
        YAxis yAxisLeft = bpLineChart.getAxisLeft();
        YAxis yAxisRight = bpLineChart.getAxisRight();
        yAxisLeft.setAxisMinimum(50f);
        yAxisLeft.setAxisMaximum(170f);
        yAxisRight.setAxisMinimum(50f);
        yAxisRight.setAxisMaximum(170f);


        // X-axis
        XAxis xAxis = bpLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //Highlighter
        lineSet_syst.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter1));
        lineSet_syst.setDrawHorizontalHighlightIndicator(false);
        lineSet_syst.setHighlightLineWidth(3f);

        lineSet_diast.setHighLightColor(ContextCompat.getColor(this, R.color.highlighter1));
        lineSet_diast.setDrawHorizontalHighlightIndicator(false);
        lineSet_diast.setHighlightLineWidth(3f);
        bpLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                lineHighlightActive = true;
                int color = getResources().getColor(R.color.highlighter1);
                int index = (int) e.getX();
                Entry eSyst = lineSet_syst.getEntryForIndex(index);
                Entry eDiast = lineSet_diast.getEntryForIndex(index);
                setGreyedOut(false);
                nowSyst.setText(Integer.toString((int)eSyst.getY()));
                nowSyst.setTextColor(color);

                nowDiast.setText(Integer.toString((int)eDiast.getY()));
                nowDiast.setTextColor(color);

                nowText.setText("CURSOR");
                nowText.setTextColor(color);
            }

            @Override
            public void onNothingSelected() {
                lineHighlightActive = false;
                int color = getResources().getColor(R.color.default_gray);
                if (barSelected) {
                    setGreyedOut(true);
                } else if (!barSelected) {
                    setGreyedOut(false);
                }
                nowSyst.setText(nowSystOrig);
                nowSyst.setTextColor(color);
                nowDiast.setText(nowDiastOrig);
                nowDiast.setTextColor(color);
                nowText.setText(nowTextOrig);
                nowText.setTextColor(color);

            }
        });

        // Zoom disabled
        bpLineChart.setScaleEnabled(false);

        // Style of line
        lineSet_syst.setLineWidth(5);
        lineSet_diast.setLineWidth(5);

        lineSet_syst.setDrawCircles(false);
        lineSet_diast.setDrawCircles(false);

        lineSet_syst.setDrawValues(false);
        lineSet_diast.setDrawValues(false);

        lineSet_syst.setColor(ContextCompat.getColor(this, R.color.BPsyst));
        lineSet_diast.setColor(ContextCompat.getColor(this, R.color.BPdiast));

        lineSet_syst.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineSet_diast.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Initiate graph
        bpLineChart.setData(lineData);

    }
    private void setGreyedOut(boolean b) {
        if (!b) {
            nowSyst.setAlpha(1f);
            nowDiast.setAlpha(1f);
            nowText.setAlpha(1f);
        }
        if (b) {
            nowSyst.setAlpha(.33f);
            nowDiast.setAlpha(.33f);
            nowText.setAlpha(.33f);
        }

    }
    private void setupBarEntries() {
        entriesBar_syst = new ArrayList<>();
        entriesBar_syst.add(new BarEntry(1, 132));
        entriesBar_syst.add(new BarEntry(2, 131));
        entriesBar_syst.add(new BarEntry(3, 123));
        entriesBar_syst.add(new BarEntry(4, 131));
        entriesBar_syst.add(new BarEntry(5, 121));
        entriesBar_syst.add(new BarEntry(6, 138));
        entriesBar_syst.add(new BarEntry(7, 135));

        entriesBar_diast = new ArrayList<>();
        entriesBar_diast.add(new BarEntry(1, 71));
        entriesBar_diast.add(new BarEntry(2, 77));
        entriesBar_diast.add(new BarEntry(3, 81));
        entriesBar_diast.add(new BarEntry(4, 71));
        entriesBar_diast.add(new BarEntry(5, 74));
        entriesBar_diast.add(new BarEntry(6, 78));
        entriesBar_diast.add(new BarEntry(7, 72));


        /*entriesBar.add(new BarEntry(1, new float[]{71,132}));
        entriesBar.add(new BarEntry(2, new float[]{77,131}));
        entriesBar.add(new BarEntry(3, new float[]{81,123}));
        entriesBar.add(new BarEntry(4, new float[]{71,131}));
        entriesBar.add(new BarEntry(5, new float[]{74,121}));
        entriesBar.add(new BarEntry(6, new float[]{78,138}));
        entriesBar.add(new BarEntry(7, new float[]{72,135}));*/
    }

    private void setupBarGraph() {
        bpBarChart = (BarChart) findViewById(R.id.bargraph_bp);
        barDataSet_syst = new BarDataSet(entriesBar_syst, "BP");
        barDataSet_diast = new BarDataSet(entriesBar_diast, "BP");

        // Zoom and hide values
        bpBarChart.setScaleEnabled(false);
        barDataSet_syst.setDrawValues(false);
        barDataSet_diast.setDrawValues(false);


        barDataSet_syst.setColor(ContextCompat.getColor(this, R.color.BPsyst));
        barDataSet_diast.setColor(ContextCompat.getColor(this, R.color.BPdiast));
        //barDataSet.setColors(getResources().getColor(R.color.BPsyst), getResources().getColor(R.color.BPdiast));
        ArrayList<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();
        barDataSets.add(barDataSet_syst);
        barDataSets.add(barDataSet_diast);
        //BarData barData = new BarData(barDataSet_syst);
        //barData.addDataSet(barDataSet_diast);
        BarData barData = new BarData(barDataSets);
        bpBarChart.setData(barData);

        Log.d("Nbr of sets", Integer.toString(barData.getDataSetCount()));

        // Highlight
        barDataSet_syst.setHighLightColor(ContextCompat.getColor(this,R.color.highlighter2));
        barDataSet_diast.setHighLightColor(ContextCompat.getColor(this,R.color.highlighter2));
        barDataSet_syst.setHighLightAlpha(150);
        barDataSet_diast.setHighLightAlpha(150);
        bpBarChart.setHighlightFullBarEnabled(true);
        bpBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {

                bpLineChart.highlightValues(null);
                barSelected = true;
                BarEntry bSyst = (BarEntry) e;
                BarEntry bDiast = getDiastEntry(bSyst);
                int color = getResources().getColor(R.color.highlighter2);
                int colorInactive = getResources().getColor(R.color.default_gray);

                avgText.setTextColor(color);
                avgSyst.setText(Integer.toString((int) bSyst.getY()));
                avgSyst.setTextColor(color);
                avgDiast.setText(Integer.toString((int) bDiast.getY()));
                avgDiast.setTextColor(color);

                nowText.setText(nowTextOrig);
                nowText.setTextColor(colorInactive);
                nowSyst.setText(nowSystOrig);
                nowSyst.setTextColor(colorInactive);
                nowDiast.setText(nowDiastOrig);
                nowDiast.setTextColor(colorInactive);

                setGreyedOut(true);
            }


            @Override
            public void onNothingSelected() {
                bpLineChart.highlightValues(null);
                barSelected = false;

                int color = getResources().getColor(R.color.default_gray);

                avgText.setTextColor(color);
                avgSyst.setText(avgSystOrig);
                avgSyst.setTextColor(color);
                avgDiast.setText(avgDiastOrig);
                avgDiast.setTextColor(color);

                nowText.setText(nowTextOrig);
                nowText.setTextColor(color);
                nowSyst.setText(nowSystOrig);
                nowSyst.setTextColor(color);
                nowDiast.setText(nowDiastOrig);
                nowDiast.setTextColor(color);


                setGreyedOut(false);
            }
        });

        // Set style on graph bg
        bpBarChart.getAxisLeft().setDrawGridLines(false);
        bpBarChart.getAxisRight().setDrawGridLines(false);
        bpBarChart.getXAxis().setDrawGridLines(false);
        bpBarChart.setDrawGridBackground(false);
        bpBarChart.setDrawBorders(false);
        bpBarChart.setDrawValueAboveBar(false);
        Description description = new Description();
        description.setText("");
        bpBarChart.setDescription(description);
        Legend legend = bpBarChart.getLegend();
        legend.setEnabled(false);
        barDataSet_syst.setValueTextColor(Color.WHITE);
        barDataSet_diast.setValueTextColor(Color.WHITE);
        barDataSet_syst.setValueTextSize(10f);
        barDataSet_diast.setValueTextSize(10f);

        // Y-axis
        YAxis yAxisLeft = bpBarChart.getAxisLeft();
        YAxis yAxisRight = bpBarChart.getAxisRight();
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(170f);
        yAxisRight.setAxisMinimum(0f);
        yAxisRight.setAxisMaximum(170f);

        XAxis xAxis = bpBarChart.getXAxis();
        xAxis.setValueFormatter(new DayValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    private BarEntry getDiastEntry(BarEntry b) {

        return barDataSet_diast.getEntryForIndex((int) b.getX()-1);
    }

}



class DayValueFormatter implements IAxisValueFormatter {

    String[] days;
    public DayValueFormatter() {
        days = new String[]{"THU","FRI","SAT","SUN","MON","TUE","TODAY"};
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return days[(int) value-1];
    }
}



