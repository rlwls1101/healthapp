package com.example.capston_project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class BroadcastD extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "12300";

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.icon_leaf)
                    .setContentTitle("시차가이드")
                    .setContentText("수면을 취해야 합니다.")
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_MAX);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                builder.setSmallIcon(R.drawable.icon_leaf); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
                CharSequence channelName = "노티페케이션 채널";
                String description = "오레오 이상을 위한 것임";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
                channel.setDescription(description);

                // 노티피케이션 채널을 시스템에 등록
                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);

            } else
                builder.setSmallIcon(R.drawable.icon_leaf); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

            assert notificationManager != null;
            notificationManager.notify(12300, builder.build());
        }
    }
}