package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.capston_project.Loginloading.user;


public class ThirdSurveyActivity extends Activity{
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton7;
    RadioButton radioButton8;
    RadioButton radioButton9;
    RadioButton radioButton10;
    EditText nap;
    EditText start;
    EditText end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_survey3);
        getObject();
        NextButton();
        BackButton();
    }
    private void getObject() {
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton7 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton8 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton9 = (RadioButton) findViewById(R.id.radioButton9);
        radioButton10 = (RadioButton) findViewById(R.id.radioButton10);
        nap=(EditText)findViewById(R.id.editText);
        start=(EditText)findViewById(R.id.editText2);
        end=(EditText)findViewById(R.id.editText3);
    }
    private void NextButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()) {
                    addUserinfo();
                    finish();
                    Intent intent = new Intent(v.getContext(), FourthSurveyActivity.class);
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
                Intent intent = new Intent(v.getContext(), SecondSurveyActivity.class);
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
        if (nap.getText().toString().replace(" ", "").equals(""))
            return false;
        if (start.getText().toString().replace(" ", "").equals(""))
            return false;
        if (end.getText().toString().replace(" ", "").equals(""))
            return false;
        return true;
    }
    private void addUserinfo(){
        if (radioButton1.isChecked())
            user.q13=1;
        else
            user.q13=0;
        if (radioButton3.isChecked())
            user.q14=1;
        else
            user.q14=0;
        user.q17=nap.getText().toString();
        if (radioButton7.isChecked())
            user.q15=1;
        else
            user.q15=0;
        if (radioButton9.isChecked())
            user.q16=1;
        else
            user.q16=0;
        user.startsleep=Integer.toString(Integer.parseInt(start.getText().toString())*60);
        user.endsleep=Integer.toString(Integer.parseInt(end.getText().toString())*60);

    }
}
