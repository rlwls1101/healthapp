package com.example.capston_project;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.example.capston_project.Loginloading.tip;
import static com.example.capston_project.Loginloading.user;

public class FifthSurveyActivity extends Activity {
    double[][] sleeptiparray = {{1, 1, 0.5, 1, 1, 0.25, 1, 1}, {1, 1, 0.5, 1, 1, 0.25, 1, 1}, {2, 2, 1, 2, 2, 0.5, 2, 2},
            {1, 1, 0.5, 1, 1, 0.25, 1, 1}, {1, 1, 0.5, 1, 1, 0.25, 1, 1}, {4, 4, 2, 4, 4, 1, 4, 4}, {1, 1, 0.5, 1, 1, 0.25, 1, 1}, {1, 1, 0.5, 1, 1, 0.25, 1, 1}};
    double[][] eatingarray = {{1, 1, 0.25, 0.25, 0.25, 0.5, 1, 1}, {1, 1, 0.25, 0.25, 0.25, 0.5, 1, 1}, {4, 4, 1, 1, 1, 2, 4, 4}, {4, 4, 1, 1, 1, 2, 4, 4}, {4, 4, 1, 1, 1, 2, 4, 4},
            {2, 2, 0.5, 0.5, 0.5, 1, 2, 2}, {1, 1, 0.25, 0.25, 0.25, 0.5, 1, 1}, {1, 1, 0.25, 0.25, 0.25, 0.5, 1, 1}};
    double[][] lifearray = {{1, 0.25, 0.25}, {4, 1, 1}, {4, 1, 1}};
    double[][] userfavorite = {{1, Math.pow(0.5, user.f1 - user.f2), Math.pow(0.5, user.f1 - user.f3)}, {1 / Math.pow(0.5, user.f1 - user.f2), 1, Math.pow(0.5, user.f2 - user.f3)},
            {1 / Math.pow(0.5, user.f1 - user.f3), 1 / Math.pow(0.5, user.f2 - user.f3), 1}};
    double[] tipresult = new double[15];
    int[] tiprank = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    CheckBox checklist1;
    CheckBox checklist2;
    CheckBox checklist3;
    CheckBox checklist4;
    CheckBox checklist5;
    CheckBox checklist6;
    int cnt = 0;
    TextView tv;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_survey5);
        getObject();
        NextButton();
        BackButton();
    }

    private void getObject() {
        checklist1 = (CheckBox) findViewById(R.id.CheckButton1);
        checklist2 = (CheckBox) findViewById(R.id.CheckButton2);
        checklist3 = (CheckBox) findViewById(R.id.CheckButton3);
        checklist4 = (CheckBox) findViewById(R.id.CheckButton4);
        checklist5 = (CheckBox) findViewById(R.id.CheckButton5);
        checklist6 = (CheckBox) findViewById(R.id.CheckButton6);
    }

    private void NextButton() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserinfo();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference mDatabaseRef = database.getReference();
                Map<String, Object> childUpdates = new HashMap<>();
                Map<String, Object> surveyValue = new HashMap<>();

                surveyValue = user.towMap();

                childUpdates.put("User" + "/" + user.id, surveyValue);

                mDatabaseRef.updateChildren(childUpdates);
                //setWeight();
                cnt++;
                Notification();
                Intent intent = new Intent(v.getContext(), SurveyResult.class);
                finish();
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
                Intent intent = new Intent(v.getContext(), FourthSurveyActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
    }

    private void addUserinfo() {
        ahp();
        int[] tipnumber = {201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215};
        for (int i = 0; i < tiprank.length; i++) {
            for (int j = i + 1; j < tiprank.length; j++) {
                if (tiprank[i] > tiprank[j]) {
                    swap(tiprank, i, j);
                    swap(tipnumber, i, j);
                }
            }
        }
        user.tipfavorite = tipnumber[0];
        user.tipfavorite2 = tipnumber[1];
        user.tipfavorite3 = tipnumber[2];
        String result = "";
        if (checklist1.isChecked() == true) result += "증상1";
        if (checklist2.isChecked() == true) result += "증상2";
        if (checklist3.isChecked() == true) result += "증상3";
        if (checklist4.isChecked() == true) result += "증상4";
        if (checklist5.isChecked() == true) result += "증상5";
        if (checklist6.isChecked() == true) result += "증상6";
        user.q19 = result;
    }


    private void ahp() {
        double[] rowsum;
        double[] sleepweight = new double[sleeptiparray.length];
        double[] eatingweight = new double[eatingarray.length];
        double[] lifeweight = new double[lifearray.length];
        double[] favorweight = new double[userfavorite.length];
        Matrix sleepmat = new Matrix(sleeptiparray);
        Matrix eatingmat = new Matrix(eatingarray);
        Matrix liftmat = new Matrix(lifearray);
        Matrix favmat = new Matrix(userfavorite);
        sleepmat = sleepmat.multiply(sleepmat);
        eatingmat = eatingmat.multiply(eatingmat);
        liftmat = liftmat.multiply(liftmat);
        favmat = favmat.multiply(favmat);
        rowsum = sleepmat.rowtotal();
        for (int i = 0; i < rowsum.length; i++) {
            sleepweight[i] = rowsum[i] / sleepmat.gettotal();
        }
        rowsum = eatingmat.rowtotal();
        for (int i = 0; i < rowsum.length; i++) {
            eatingweight[i] = rowsum[i] / eatingmat.gettotal();
        }
        rowsum = liftmat.rowtotal();
        for (int i = 0; i < rowsum.length; i++) {
            lifeweight[i] = rowsum[i] / liftmat.gettotal();
        }
        rowsum = favmat.rowtotal();
        for (int i = 0; i < rowsum.length; i++) {
            favorweight[i] = rowsum[i] / favmat.gettotal();
        }

        for (int i = 0; i < sleepweight.length; i++) {
            sleepweight[i] = sleepweight[i] * favorweight[0]; //201 204 205 206 208 209 214 215
        }
        for (int i = 0; i < eatingweight.length; i++) {
            eatingweight[i] = eatingweight[i] * favorweight[1]; //201 202 203 207 210 213 214 215
        }
        for (int i = 0; i < lifeweight.length; i++) {
            lifeweight[i] = lifeweight[i] * favorweight[2]; //206 211 212
        }
        tipresult[0] = sleepweight[0] + eatingweight[0];//201
        tipresult[1] = eatingweight[1];// 202
        tipresult[2] = eatingweight[2];// 203
        tipresult[3] = sleepweight[1];// 204
        tipresult[4] = sleepweight[2];// 205
        tipresult[5] = sleepweight[3] + lifeweight[0];// 206
        tipresult[6] = eatingweight[3];// 207
        tipresult[7] = sleepweight[4];// 208
        tipresult[8] = sleepweight[5];// 209
        tipresult[9] = eatingweight[4];// 210
        tipresult[10] = lifeweight[1];// 211
        tipresult[11] = lifeweight[2];// 212
        tipresult[12] = eatingweight[5];// 213
        tipresult[13] = sleepweight[6] + eatingweight[6];// 214
        tipresult[14] = sleepweight[7] + eatingweight[7];// 215

        for (int i = 0; i < tipresult.length; i++) {
            for (int j = 0; j < tipresult.length; j++) {
                if (tipresult[i] < tipresult[j]) {
                    tiprank[i]++;
                }
            }
        }
    }

    public void swap(int[] array, int i1, int i2) {
        int temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    private void Notification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, TipResult.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_leaf)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_leaf)) //BitMap 이미지 요구
                .setContentTitle("오늘의 팁!")
                .setContentText("Tip1. " + checktip(user.tipfavorite))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시TipResult로 이동하도록 설정
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Tip1. " + checktip(user.tipfavorite) + "\nTip2. " + checktip(user.tipfavorite2) + "\nTip3. " + checktip(user.tipfavorite3)))
                .setPriority(NotificationCompat.PRIORITY_MAX);


        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName = "노티페케이션 채널";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        } else
            builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert notificationManager != null;
        notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴

    }

    public String checktip(int number) {
        if (number == 201) {
            return tip.getT201();
        }
        if (number == 202) {
            return tip.getT202();
        }
        if (number == 203) {
            return tip.getT203();
        }
        if (number == 204) {
            return tip.getT204();
        }
        if (number == 205) {
            return tip.getT205();
        }
        if (number == 206) {
            return tip.getT206();
        }
        if (number == 207) {
            return tip.getT207();
        }
        if (number == 208) {
            return tip.getT208();
        }
        if (number == 209) {
            return tip.getT209();
        }
        if (number == 210) {
            return tip.getT210();
        }
        if (number == 211) {
            return tip.getT211();
        }
        if (number == 212) {
            return tip.getT212();
        }
        if (number == 213) {
            return tip.getT213();
        }
        if (number == 214) {
            return tip.getT214();
        }
        if (number == 215) {
            return tip.getT215();
        }
        return "오류";
    }
}
