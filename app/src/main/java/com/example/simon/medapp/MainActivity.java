package com.example.simon.medapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void cardHeartClicked(View view) {
        Intent i = new Intent(MainActivity.this, HeartActivity.class);
        startActivity(i);
    }

    /* public void cardBPClicked(View view) {
        Intent i = new Intent(MainActivity.this, HeartActivity.class);
        startActivity(i);
    }

    public void cardPEFClicked(View view) {
        Intent i = new Intent(MainActivity.this, HeartActivity.class);
        startActivity(i);
    }

    public void cardSleepClicked(View view) {
        Intent i = new Intent(MainActivity.this, HeartActivity.class);
        startActivity(i);
    }

    public void cardTemperatureClicked(View view) {
        Intent i = new Intent(MainActivity.this, HeartActivity.class);
        startActivity(i);
    } */

}
