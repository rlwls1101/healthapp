package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.capston_project.Loginloading.user;


public class SecondSurveyActivity extends Activity{
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    EditText meal;
    RadioButton radioButton7;
    RadioButton radioButton8;
    RadioButton radioButton9;
    RadioButton radioButton10;
    RadioButton radioButton11;
    RadioButton radioButton12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_survey2);
        getObject();
        NextButton();
        BackButton();
    }

    private void getObject() {
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        meal=(EditText)findViewById(R.id.editText);
        radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton8 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton9 = (RadioButton) findViewById(R.id.radioButton9);
        radioButton10 = (RadioButton) findViewById(R.id.radioButton10);
        radioButton11 = (RadioButton) findViewById(R.id.radioButton11);
        radioButton12= (RadioButton) findViewById(R.id.radioButton12);
    }

    private void NextButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) {
                    addUserinfo();
                    finish();
                    Intent intent = new Intent(v.getContext(), ThirdSurveyActivity.class);
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
                Intent intent = new Intent(v.getContext(), FirstSurveyActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private boolean isEmpty() {
        if (!radioButton1.isChecked() && !radioButton2.isChecked())
            return false;
        if (!radioButton3.isChecked() && !radioButton4.isChecked())
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
            user.q7=1;
        else
            user.q7=0;
        if (radioButton3.isChecked())
            user.q8=1;
        else
            user.q8=0;
        user.q9=meal.getText().toString();
        if (radioButton7.isChecked())
            user.q10=1;
        else
            user.q10=0;
        if (radioButton9.isChecked())
            user.q11=1;
        else
            user.q11=0;
        if (radioButton11.isChecked())
            user.q12=1;
        else
            user.q12=0;
    }
}
