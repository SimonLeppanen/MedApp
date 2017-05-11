package com.example.simon.medapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView measurement1;
    private TextView measurement2;
    private TextView measurement3;
    private View pefLine1;
    private View pefLine2;
    private Context ctx;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pef_add_1);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader2)));

        setupTextStyles();
        ctx = getApplicationContext();
        handler = new Handler();
        procedure();
    }

    private void procedure() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PEFActivityAdd.this, "Device connected!", Toast.LENGTH_SHORT).show();
                connectDevice();
            }
        },3000);
    }

    private void setupTextStyles() {
        text1 = (TextView) findViewById(R.id.text_1);
        text2 = (TextView) findViewById(R.id.text_2);
        text3 = (TextView) findViewById(R.id.text_3);
        nbr1 = (TextView) findViewById(R.id.nbr_1);
        nbr2 = (TextView) findViewById(R.id.nbr_2);
        nbr3 = (TextView) findViewById(R.id.nbr_3);
        check1 = (ImageView) findViewById(R.id.check_1);
        check2 = (ImageView) findViewById(R.id.check_2);
        check3 = (ImageView) findViewById(R.id.check_3);
        measurement1 = (TextView) findViewById(R.id.measurement_1);
        measurement2 = (TextView) findViewById(R.id.measurement_2);
        measurement3 = (TextView) findViewById(R.id.measurement_3);
        pefLine1 = findViewById(R.id.pef_line1);
        pefLine2 = findViewById(R.id.pef_line2);

        text2.setAlpha(.33f);
        text3.setAlpha(.33f);

        nbr2.setAlpha(.33f);
        nbr3.setAlpha(.33f);

        measurement1.setAlpha(.33f);
        measurement1.setText("Measurement 1");
        measurement1.setTextSize(24);
        measurement1.setTypeface(null, Typeface.NORMAL);


        measurement2.setAlpha(.33f);
        measurement2.setText("Measurement 2");
        measurement2.setTextSize(24);
        measurement2.setTypeface(null, Typeface.NORMAL);

        measurement3.setAlpha(.33f);
        measurement3.setText("Measurement 3");
        measurement3.setTextSize(24);
        measurement3.setTypeface(null, Typeface.NORMAL);

        pefLine1.setAlpha(.33f);
        pefLine2.setAlpha(.33f);

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setAlpha(.33f);
        nextButton.setClickable(false);



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
                takeMeasurement1();
            }
        },3000);
    }

    private void takeMeasurement1() {

        measurement1.setText("Take measurement 1");
        measurement1.setAlpha(1f);
        measurement1.setTypeface(null, Typeface.BOLD);

        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                measurement1.setText("490");
                measurement1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                measurement1.setTextColor(ContextCompat.getColor(ctx, R.color.addbuttonbackground));
                takemeasurement2();
            }
        }, 4000);
    }

    private void takemeasurement2() {
        measurement2.setText("Take measurement 2");
        measurement2.setAlpha(1f);

        pefLine1.setAlpha(1f);
        pefLine1.setBackgroundColor(ContextCompat.getColor(ctx,R.color.addbuttonbackground));

        measurement2.setTypeface(null, Typeface.BOLD);

        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                measurement2.setText("480");
                measurement2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                measurement2.setTextColor(ContextCompat.getColor(ctx, R.color.addbuttonbackground));
                measurement2.setTypeface(null, Typeface.NORMAL);
                measurement2.setAlpha(.67f);
                takemeasurement3();
            }
        }, 4000);
    }

    private void takemeasurement3() {

        measurement3.setText("Take measurement 3");
        measurement3.setAlpha(1f);
        measurement3.setTypeface(null, Typeface.BOLD);

        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                measurement1.setTypeface(null, Typeface.NORMAL);
                measurement1.setAlpha(.67f);

                pefLine2.setAlpha(1f);
                pefLine2.setBackgroundColor(ContextCompat.getColor(ctx,R.color.addbuttonbackground));


                measurement3.setText("505");
                measurement3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
                measurement3.setTextColor(ContextCompat.getColor(ctx, R.color.addbuttonbackground));

                done();
            }
        }, 4000);
    }

    private void done() {
        nbr2.setVisibility(View.GONE);
        check2.setVisibility(View.VISIBLE);
        text2.setAlpha(.33f);
        nbr3.setAlpha(1f);
        text3.setAlpha(1f);
        nextButton.setAlpha(1f);
        nextButton.setClickable(true);
    }

    public void nextClick(View view) {
        Intent intent = new Intent(PEFActivityAdd.this,PEFActivityAdd2.class);
        startActivityForResult(intent,16);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 16) {
            if (resultCode == 16) {
                this.finish();
            }
        }
    }
}

