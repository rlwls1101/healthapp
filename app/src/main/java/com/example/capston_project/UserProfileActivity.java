package com.example.capston_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.samsung.android.sdk.healthdata.HealthUserProfile;

public class UserProfileActivity extends Fragment {
    HealthUserProfile usrProfile = HealthUserProfile.getProfile(MainActivity.mStore);
    public static UserProfileActivity newInstance() {
        return new UserProfileActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.user_profile, container, false);
        TextView textview1 = v.findViewById(R.id.textView6);
        TextView textview2 = v.findViewById(R.id.textView7);
        TextView textview3 = v.findViewById(R.id.textView8);
        TextView textview4 = v.findViewById(R.id.textView9);
        if (usrProfile.getGender() == HealthUserProfile.GENDER_MALE) {
            textview1.setText("남");
        }else
            textview1.setText("여");
        textview2.setText(usrProfile.getBirthDate().substring(0,4));
        textview3.setText(Float.toString(usrProfile.getHeight()));
        textview4.setText(Float.toString(usrProfile.getWeight()));
        return v;
    }
}

