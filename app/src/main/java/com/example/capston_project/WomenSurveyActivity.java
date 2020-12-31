package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.capston_project.Loginloading.user;


public class WomenSurveyActivity extends Activity{
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton5;
    RadioButton radioButton6;
    EditText day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.women_survey);
        getObject();
        NextButton();
        BackButton();
    }
    private void getObject() {
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        day=(EditText)findViewById(R.id.editText);
        radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        radioButton6 = (RadioButton) findViewById(R.id.radioButton6);
    }
    private void NextButton() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserinfo();
                Intent intent = new Intent(v.getContext(), FirstSurveyActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
    private void BackButton() {
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
    private void addUserinfo(){
        if (radioButton1.isChecked())
            user.w2=1;
        else
            user.w2=0;
        if (radioButton3.isChecked())
            user.w3=1;
        else
            user.w3=0;
        user.w1=day.getText().toString();
        if (radioButton5.isChecked())
            user.w4=1;
        else
            user.w4=0;
    }
}
