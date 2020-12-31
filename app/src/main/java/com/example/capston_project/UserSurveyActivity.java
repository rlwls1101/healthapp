package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.samsung.android.sdk.healthdata.HealthUserProfile;

import static com.example.capston_project.Loginloading.user;

public class UserSurveyActivity extends Activity {

    EditText editText;
    EditText editText2;
    EditText editText3;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RatingBar ratingstar1;
    RatingBar ratingstar2;
    RatingBar ratingstar3;
    HealthUserProfile usrProfile = HealthUserProfile.getProfile(MainActivity.mStore);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_survey);
        getObject();
        getUserProfile();
        checkrate();
        NextButton();
        BackButton();
    }

    private void NextButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty()&&checkId()) {
                    addUserinfo();
                    finish();
                    if(user.sex==1) {
                        Intent intent = new Intent(v.getContext(), FirstSurveyActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                    else {
                        Intent intent = new Intent(v.getContext(), WomenSurveyActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
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

    private void getObject() {
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        ratingstar1=(RatingBar)findViewById(R.id.ratingBar);
        ratingstar2=(RatingBar)findViewById(R.id.ratingBar1);
        ratingstar3=(RatingBar)findViewById(R.id.ratingBar2);
    }

    private void getUserProfile() {

        if (!usrProfile.getBirthDate().isEmpty())
            editText.setText(usrProfile.getBirthDate().substring(0,4));
        if (usrProfile.getHeight() != 0.0f)
            editText2.setText(Float.toString(usrProfile.getHeight()));
        if (usrProfile.getWeight() != 0.0f)
            editText3.setText(Float.toString(usrProfile.getWeight()));
        if (usrProfile.getGender() == HealthUserProfile.GENDER_MALE) {
            radioButton1.setChecked(true);
        } else if (usrProfile.getGender() == HealthUserProfile.GENDER_FEMALE) {
            radioButton2.setChecked(true);
        }
    }

    private boolean isEmpty() {
        if (editText.getText().toString().replace(" ", "").equals(""))
            return false;
        if (editText2.getText().toString().replace(" ", "").equals(""))
            return false;
        if (editText3.getText().toString().replace(" ", "").equals(""))
            return false;
        if (!radioButton1.isChecked() && !radioButton2.isChecked())
            return false;
        return true;
    }
    private boolean checkId(){
        if(user.id != null)
            return true;
        else {
            Toast.makeText(UserSurveyActivity.this, "로그인한후 이용해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void addUserinfo(){
       user.age=Integer.parseInt(editText.getText().toString());
       user.height=Float.parseFloat(editText2.getText().toString());
       user.weight=Float.parseFloat(editText3.getText().toString());
       if(radioButton1.isChecked())
           user.sex=1; //남자
       else
           user.sex=2; //여자
    }
    private void checkrate(){
        ratingstar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                user.f1=rating;
            }
        });
        ratingstar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                user.f2=rating;
            }
        });
        ratingstar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                user.f3=rating;
            }
        });
    }
}
