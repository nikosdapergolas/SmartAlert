package com.unipi.nickdap.p19039.smartalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        CountDownTimer timer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                NotificationChannel channel = new NotificationChannel("123","channelUnipi",
                        NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getApplicationContext(),"123");
                builder.setContentTitle("EMERGENCY")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentText("Please log in Quickly into the app to see the emergency situation. Important!!!")
                        .setAutoCancel(true);
                notificationManager.notify(1,builder.build());
            }
        };
        timer.start();
    }
}