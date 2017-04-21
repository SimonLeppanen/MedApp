package com.example.simon.medapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by simon on 2017-04-19.
 */

public class PEFActivityMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pef_main);

    }

    public void pefAddButton(View view) {
        Intent intent = new Intent(PEFActivityMain.this, PEFActivityAdd.class);
        startActivity(intent);

    }

    public void pefEntryClicked(View view) {
        TableRow row = (TableRow) view;
        TextView textView1 = (TextView) row.getChildAt(0);
        TextView textView2 = (TextView) row.getChildAt(1);
        TextView textView3 = (TextView) row.getChildAt(2);

        String day = textView1.getText().toString();
        String time = textView2.getText().toString();
        String value = textView3.getText().toString();

        Intent intent = new Intent(PEFActivityMain.this, PEFActivityEntry.class);
        intent.putExtra("DAY", day);
        intent.putExtra("TIME", time);
        intent.putExtra("VALUE", value);
        startActivity(intent);

    }
}
