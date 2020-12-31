package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import static com.example.capston_project.Loginloading.user;


public class FourthSurveyActivity extends Activity{
    CheckBox checklist1;
    CheckBox checklist2;
    CheckBox checklist3;
    CheckBox checklist4;
    CheckBox checklist5;
    CheckBox checklist6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_survey4);
        getObject();
        NextButton();
        BackButton();
    }
    private void getObject() {
        checklist1=(CheckBox)findViewById(R.id.CheckButton1);
        checklist2=(CheckBox)findViewById(R.id.CheckButton2);
        checklist3=(CheckBox)findViewById(R.id.CheckButton3);
        checklist4=(CheckBox)findViewById(R.id.CheckButton4);
        checklist5=(CheckBox)findViewById(R.id.CheckButton5);
        checklist6=(CheckBox)findViewById(R.id.CheckButton6);
    }
    private void NextButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserinfo();
                finish();
                Intent intent = new Intent(v.getContext(), FifthSurveyActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
    }
    private void BackButton() {
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(v.getContext(), ThirdSurveyActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
    }
    private void addUserinfo(){
        String result ="";
        if(checklist1.isChecked()==true) result+="증상1";
        if(checklist2.isChecked()==true) result+="증상2";
        if(checklist3.isChecked()==true) result+="증상3";
        if(checklist4.isChecked()==true) result+="증상4";
        if(checklist5.isChecked()==true) result+="증상5";
        if(checklist6.isChecked()==true) result+="증상6";
        user.q18=result;
    }
}
