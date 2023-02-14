package com.unipi.nickdap.p19039.smartalert;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        getFirebaseMessage(message.getNotification().getTitle(),message.getNotification().getBody());
    }

    public void getFirebaseMessage(String title, String message)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"My notification")
                .setSmallIcon(R.drawable.ic_baseline_warning_amber_24)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(123,builder.build());
    }
}
