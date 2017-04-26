package com.example.simon.medapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;

import java.io.Serializable;
import java.util.Calendar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by simon on 2017-04-24.
 */

public class StartActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView ageTV;
    private birthDay birthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViewById(R.id.gender_spinner).post(new Runnable() {
            @Override
            public void run() {
                setGenderSpinner();
            }
        });
        findViewById(R.id.age_tv).post(new Runnable() {
            @Override
            public void run() {
                setAgePicker();
            }
        });
    }

    private void setGenderSpinner() {

        Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    TextView text = ((TextView)v.findViewById(android.R.id.text1));
                    text.setText("");
                    text.setTextSize(16);
                    text.setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.add("Female");
        genderAdapter.add("Male");
        genderAdapter.add("Other");
        genderAdapter.add("Select gender:");
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setSelection(genderAdapter.getCount());
        genderSpinner.setOnItemSelectedListener(this);
    }

    private void setAgePicker() {
        ageTV = (TextView) findViewById(R.id.age_tv);
        EditText toFocus = (EditText) findViewById(R.id.weight_edittext);
        toFocus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        birthday = new birthDay(ageTV, toFocus, this);
    }


    public void okClick(View v) {
        int weight;
        int height;
        String gender;
        EditText weight_et = (EditText) findViewById(R.id.weight_edittext);
        EditText height_et = (EditText) findViewById(R.id.height_edittext);
        Toast t = Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_LONG);

        Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        gender = genderSpinner.getSelectedItem().toString();
        try {
            weight = Integer.parseInt(weight_et.getText().toString());
            height = Integer.parseInt(height_et.getText().toString());
            if (gender != "Male" && gender != "Female" && gender != "Other") {
                t.show();
                return;
            }
        } catch (NumberFormatException e){

            t.show();
            return;
        }
            Person person = new Person(weight, height, gender, birthday);
            Intent i = new Intent();
            i.putExtra("PERSON", person);
            i.setClass(this, MainActivity.class);
            startActivity(i);
        }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}



class birthDay implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView tv;
    private EditText toFocus;
    private Calendar cal;
    private int year;
    private Context context;

    public birthDay(TextView tv, EditText toFocus, Context context) {
        this.tv = tv;
        this.tv.setOnClickListener(this);
        this.toFocus = toFocus;
        this.context = context;
        cal = Calendar.getInstance();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        CharSequence text = "  " + Integer.toString(cal.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(cal.get(Calendar.MONTH)+1) + "/" + Integer.toString(cal.get(Calendar.YEAR));
        tv.setText(text);
        toFocus.requestFocus();

    }

    @Override
    public void onClick(View view) {
        DatePickerDialog d = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }

    public Calendar getCal() {
        return (Calendar) cal.clone();
    }
}

class Person implements Serializable {

    private int weight;
    private int height;
    private String gender;
    Calendar cal;

    public Person(int weight, int height, String gender, birthDay birthday) {
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.cal = birthday.getCal();

    }

}
