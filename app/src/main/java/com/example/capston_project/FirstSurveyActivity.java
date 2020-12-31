package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import static com.example.capston_project.Loginloading.user;


public class FirstSurveyActivity extends Activity{

    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton5;
    RadioButton radioButton6;
    RadioButton radioButton7;
    RadioButton radioButton8;
    RadioButton radioButton9;
    RadioButton radioButton10;
    RadioButton radioButton11;
    RadioButton radioButton12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_survey1 );
        getObject();
        NextButton();
        BackButton();
    }
    private void getObject() {
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        radioButton6 = (RadioButton) findViewById(R.id.radioButton6);
        radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton8 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton9 = (RadioButton) findViewById(R.id.radioButton9);
        radioButton10 = (RadioButton) findViewById(R.id.radioButton10);
        radioButton11 = (RadioButton) findViewById(R.id.radioButton11);
        radioButton12 = (RadioButton) findViewById(R.id.radioButton12);
    }
    private void NextButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) {
                    addUserinfo();
                    finish();
                    Intent intent = new Intent(v.getContext(), SecondSurveyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                }
            }
        });
    }
    private void BackButton() {
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
    private boolean isEmpty() {

        if (!radioButton1.isChecked() && !radioButton2.isChecked())
            return false;
        if (!radioButton3.isChecked() && !radioButton4.isChecked())
            return false;
        if (!radioButton5.isChecked() && !radioButton6.isChecked())
            return false;
        if (!radioButton7.isChecked() && !radioButton8.isChecked())
            return false;
        if (!radioButton9.isChecked() && !radioButton10.isChecked())
            return false;
        if (!radioButton11.isChecked() && !radioButton12.isChecked())
            return false;
        return true;
    }

    private void addUserinfo(){
        if (radioButton1.isChecked())
            user.q1=1;
        else
            user.q1=0;
        if (radioButton3.isChecked())
            user.q2=1;
        else
            user.q2=0;
        if (radioButton5.isChecked())
            user.q3=1;
        else
            user.q3=0;
        if (radioButton7.isChecked())
            user.q4=1;
        else
            user.q4=0;
        if (radioButton9.isChecked())
            user.q5=1;
        else
            user.q5=0;
        if (radioButton11.isChecked())
            user.q6=1;
        else
            user.q6=0;
    }
}
