package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.capston_project.Loginloading.result;
import static com.example.capston_project.Loginloading.user;

public class SurveyResult extends Activity {

    private LinearLayout dynamicLayout;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_result);
        num=1;
        setResult();
       NextButton();
    }

    private void setResult() {
        dynamicLayout = (LinearLayout)findViewById(R.id.dynamicArea);
        dynamicLayout.removeAllViews();//다이나믹 레이아웃 초기화

        IntMakeResult(user.q1, result.getSmoke());
        IntMakeResult(user.q2, result.getsSmoke());
        IntMakeResult(user.q3, result.getDrink());
        IntMakeResult(user.q4, result.getsDrink());
        IntMakeResult(user.q5, result.getOuting());
        IntMakeResult(user.q8, result.getrMeal());
        FewMakeResult(Integer.parseInt(user.q9), result.getfMeal());
        MuchMakeResult(Integer.parseInt(user.q9), result.getmMeal());
        ZeroMakeResult(user.q11, result.getrMeal());
        IntMakeResult(user.q12, result.getsMeal());
        IntMakeResult(user.q13, result.getCaffeine());
        IntMakeResult(user.q14, result.getaCaffeine());
        IntMakeResult(user.q15, result.getrSleep());
        IntMakeResult(user.q16, result.getNap());
        BooleanMakeResult(!user.q18.isEmpty(), result.getQ18());
        BooleanMakeResult(user.q18.contains("증상1"), result.getQ18_1());
        BooleanMakeResult(user.q18.contains("증상2"), result.getQ18_2());
        BooleanMakeResult(user.q18.contains("증상3"), result.getQ18_3());
        BooleanMakeResult(user.q18.contains("증상4"), result.getQ18_4());
        BooleanMakeResult(user.q18.contains("증상5"), result.getQ18_5());
        BooleanMakeResult(user.q18.contains("증상6"), result.getQ18_6());
    }

    public void IntMakeResult(int q, String result) {
        if (q==1) {
            TextView view = new TextView(SurveyResult.this);
            view.setId(num);
            view.setText(num + ". " + result);
            dynamicLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            num += 1;
        }
    }

    public void ZeroMakeResult(int q, String result) {
        if (q==0) {
            TextView view = new TextView(SurveyResult.this);
            view.setId(num);
            view.setText(num + ". " + result);
            dynamicLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            num += 1;
        }
    }

    public void FewMakeResult(int q, String result) {
        if (q <= 1) {
            TextView view = new TextView(SurveyResult.this);
            view.setId(num);
            view.setText(num + ". " + result);
            dynamicLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            num += 1;
        }
    }

    public void MuchMakeResult(int q, String result) {
        if (q >= 4) {
            TextView view = new TextView(SurveyResult.this);
            view.setId(num);
            view.setText(num + ". " + result);
            dynamicLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            num += 1;
        }
    }
    public void BooleanMakeResult(boolean q, String result) {
        if (q) {
            TextView view = new TextView(SurveyResult.this);
            view.setId(num);
            view.setText(num + ". " + result);
            dynamicLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            num += 1;
        }
    }


    private void NextButton() {
        findViewById(R.id.Button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(v.getContext(), UserSurveyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
            }
        });
    }

}
