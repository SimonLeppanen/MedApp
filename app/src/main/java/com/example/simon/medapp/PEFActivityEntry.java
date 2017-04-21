package com.example.simon.medapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by simon on 2017-04-19.
 */

public class PEFActivityEntry extends AppCompatActivity {

    String day;
    String time;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pef_entry);

        Bundle extras = getIntent().getExtras();

        if(extras == null) {
            day = null;
            time = null;
            value = null;
        } else {
            day = extras.getString("DAY");
            time = extras.getString("TIME");
            value = extras.getString("VALUE");
        }
        TextView dayTextView = (TextView) findViewById(R.id.entry_day);
        TextView timeTextView = (TextView) findViewById(R.id.entry_time);
        TextView valueTextView = (TextView) findViewById(R.id.entry_value);

        dayTextView.setText(day);
        timeTextView.setText(time);
        valueTextView.setText(value);


    }
}
