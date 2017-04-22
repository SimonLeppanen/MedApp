package com.example.simon.medapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    ActionBarDrawerToggle mDrawerToggle;
    String[] menuTitles;
    DrawerLayout drawerLayout;
    ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setUpDrawerToggle();

    }

    public void cardHeartClicked(View view) {
        Intent i = new Intent(MainActivity.this, HeartActivity.class);
        startActivity(i);
    }

    public void cardBPClicked(View view) {
        Intent i = new Intent(MainActivity.this, BPActivity.class);
        startActivity(i);
    }

    public void cardPEFClicked(View view) {
        Intent i = new Intent(MainActivity.this, PEFActivityMain.class);
        startActivity(i);
    }

    public void cardSleepClicked(View view) {
        Intent i = new Intent(MainActivity.this, SleepActivity.class);
        startActivity(i);
    }

    public void cardTemperatureClicked(View view) {
        Intent i = new Intent(MainActivity.this, TemperatureActivity.class);
        startActivity(i);
    }

    /*private void setUpDrawerToggle() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);

        //ActionBarDrawerToggle ties together the the proper interactions between the navigation drawer and the action bar app icon.
        menuTitles = getResources().getStringArray(R.array.array_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter());
        // Set the list's click listener
        //drawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle("onclosed");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("onopened");
            }
        };
    }*/
}






