package com.example.capston_project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.capston_project.Loginloading.user;

public class AddTrip extends Activity {
    final Calendar cal = Calendar.getInstance();
    private LinearLayout dynamicLayout;
    Button button1;
    Button button2;
    int yy;
    int mm;
    int dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtrip);
        dynamicLayout = (LinearLayout) findViewById(R.id.dynamicArea);
        dynamicLayout.removeAllViews();//다이나믹 레이아웃 초기화
        AddTrip();


    }
    private void AddTrip(){
        if(user.arrivalcountry==null){
            button1 = new Button(AddTrip.this);
            button1.setId(1013+6);
            button1.setText("+일정 추가");
            dynamicLayout.addView(button1, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            AddTripButton();
            return;
        }
        TextView view3 = new TextView(AddTrip.this);
        view3.setId(1013+3);
        view3.setText("출발공항: "+user.departureport+", "+user.departurecountry );
        dynamicLayout.addView(view3, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView view4 = new TextView(AddTrip.this);
        view4.setId(1013+4);
        view4.setText("출발시간: "+user.departuretime );
        dynamicLayout.addView(view4, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView view = new TextView(AddTrip.this);
        view.setId(1013+1);
        view.setText("도착공항: "+user.arrivalport+", "+user.arrivalcountry );
        dynamicLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView view2 = new TextView(AddTrip.this);
        view2.setId(1013+2);
        view2.setText("도착시간: "+user.arrivaltime );
        dynamicLayout.addView(view2, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button2 = new Button(AddTrip.this);
        button2.setId(1013+5);
        button2.setText("일정 제거");
        dynamicLayout.addView(button2, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        DeleteTripButton();

    }
    private void DeleteTripButton() {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = database.getReference();
                Map<String, Object> childUpdates = new HashMap<>();
                Map<String, Object> surveyValue;
                user.arrivalcountry=null;
                user.arrivalport=null;
                user.arrivaltime=null;
                user.departurecountry=null;
                user.departureport=null;
                user.departuretime=null;
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                overridePendingTransition(0, 0);
                surveyValue = user.towMap();
                childUpdates.put("User" + "/" + user.id, surveyValue);
                mDatabaseRef.updateChildren(childUpdates);
            }
        });
    }
    private void AddTripButton() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });
    }


    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTrip.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                yy=year;
                                mm=month+1;
                                dd=day;
                                Intent intent = new Intent(getApplicationContext(), AddFlight.class);
                                intent.putExtra("yy",yy);
                                intent.putExtra("mm",mm);
                                intent.putExtra("dd",dd);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                return datePickerDialog;
        }
        return super.onCreateDialog(id);
    }
}