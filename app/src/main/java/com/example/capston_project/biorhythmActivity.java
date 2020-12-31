package com.example.capston_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class biorhythmActivity extends Fragment {
    Button mbpmCountTv;
    Button mmgdlCountTv;
    Button mmmhgCountTv;
    Button mmmhgCountTv2;
    private HeartRateReporter mHeartRateReporter;
    private BloodglucoseReporter mBloodglucoseReporter;
    private BloodpressureReporter mBloodpressureReporter;

    public static biorhythmActivity newInstance() {
        return new biorhythmActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private HeartRateReporter.HeartRateObserver mHeartRateObserver = count -> {
        updatebpmCountView(String.valueOf(count));
    };

    private BloodglucoseReporter.BloodglucoseObserver mBloodglucoseObserver = count -> {
        updatemgdlCountView(String.valueOf(count));
    };

    private BloodpressureReporter.BloodpressureObserver mBloodpressureObserver = count -> {
        updatemmhdCountView(String.valueOf(count[0]));
        updatemmhdCount2View(String.valueOf(count[1]));
    };


    private void updatebpmCountView(final String count) {
        getActivity().runOnUiThread(() -> mbpmCountTv.setText(count));
    }
    private void updatemgdlCountView(final String count) {
        getActivity().runOnUiThread(() -> mmgdlCountTv.setText(count));
    }
    private void updatemmhdCountView(final String count) {
        getActivity().runOnUiThread(() -> mmmhgCountTv.setText(count));
    }
    private void updatemmhdCount2View(final String count) {
        getActivity().runOnUiThread(() -> mmmhgCountTv2.setText(count));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.biorhythm_main, container, false);
        mHeartRateReporter = new HeartRateReporter(MainActivity.mStore);
        mBloodglucoseReporter = new BloodglucoseReporter(MainActivity.mStore);
        mBloodpressureReporter = new BloodpressureReporter(MainActivity.mStore);
        mbpmCountTv = v.findViewById(R.id.editHealthDateValue1);
        mmgdlCountTv = v.findViewById(R.id.editHealthDateValue2);
        mmmhgCountTv = v.findViewById(R.id.editHealthDateValue3);
        mmmhgCountTv2 = v.findViewById(R.id.editHealthDateValue4);

        mHeartRateReporter.start(mHeartRateObserver);
        mBloodglucoseReporter.start(mBloodglucoseObserver);
        mBloodpressureReporter.start(mBloodpressureObserver);
        return v;
    }
}
