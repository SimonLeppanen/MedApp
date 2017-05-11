package com.example.simon.medapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PEFActivityAdd2 extends AppCompatActivity {


    private TextView nbr3;
    private TextView nbr4;
    private ImageView check3;
    private ImageView check4;
    private TextView text3;
    private TextView text4;
    private EditText et1;
    private EditText et2;
    private RadioGroup radioGroup;
    private RadioButton rb3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pef_add_2);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorCardHeader2)));
        setupTexts();
    }

    private void setupTexts() {
        et1 = (EditText) findViewById(R.id.et_1);
        et2 = (EditText) findViewById(R.id.et_2);
        et1.setFocusable(false);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        rb3 = (RadioButton) findViewById(R.id.radio3);

        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.getId() == et2.getId()) {
                    if (!rb3.isChecked()) {
                        Log.d("et focussed", "yes");
                        rb3.setChecked(true);
                    }

                }

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == rb3.getId() && !et2.isFocused()) {
                    Log.d("rb checked", "yes");
                    et2.requestFocus();
                } else {
                    rb3.setChecked(false);
                    et2.clearFocus();
                }
            }
        });
    }


    public void addClick(View view) {
        setResult(16);
        super.onActivityResult(16,16,this.getIntent());
        finish();


    }


}
