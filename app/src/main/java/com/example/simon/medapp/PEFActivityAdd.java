package com.example.simon.medapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by simon on 2017-04-19.
 */

public class PEFActivityAdd extends AppCompatActivity {


    Handler handler;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    private TextView nbr1;
    private TextView nbr2;
    private TextView nbr3;
    private TextView nbr4;
    private ImageView check1;
    private ImageView check2;
    private ImageView check3;
    private ImageView check4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pef_add);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader2)));

        setupTextStyles();

        handler = new Handler();
        procedure();
    }

    private void procedure() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connectDevice();
            }
        },3000);
    }

    private void setupTextStyles() {
        text1 = (TextView) findViewById(R.id.text_1);
        text2 = (TextView) findViewById(R.id.text_2);
        text3 = (TextView) findViewById(R.id.text_3);
        text4 = (TextView) findViewById(R.id.text_4);
        nbr1 = (TextView) findViewById(R.id.nbr_1);
        nbr2 = (TextView) findViewById(R.id.nbr_2);
        nbr3 = (TextView) findViewById(R.id.nbr_3);
        nbr4 = (TextView) findViewById(R.id.nbr_4);
        check1 = (ImageView) findViewById(R.id.check_1);
        check2 = (ImageView) findViewById(R.id.check_2);
        check3 = (ImageView) findViewById(R.id.check_3);
        check4 = (ImageView) findViewById(R.id.check_4);


        text2.setAlpha(.33f);
        text3.setAlpha(.33f);
        text4.setAlpha(.33f);
        nbr2.setAlpha(.33f);
        nbr3.setAlpha(.33f);
        nbr4.setAlpha(.33f);
    }

    private void connectDevice() {
        nbr1.setVisibility(View.GONE);
        check1.setVisibility(View.VISIBLE);
        text1.setAlpha(.33f);
        text2.setAlpha(1f);
        nbr2.setAlpha(1f);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                takeMeasurement();
            }
        },3000);
    }

    private void takeMeasurement() {
        nbr2.setVisibility(View.GONE);
        check2.setVisibility(View.VISIBLE);
        text2.setAlpha(.33f);
        nbr3.setAlpha(1f);
        text3.setAlpha(1f);
    }


    public void addClick(View view) {
        finish();

    }
}
