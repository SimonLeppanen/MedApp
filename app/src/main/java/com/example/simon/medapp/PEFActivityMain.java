package com.example.simon.medapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
}
