package com.afaq.huts.messeges;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.afaq.huts.R;
import com.afaq.huts.ui.ChatsActivity;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Random;

public class MessegeClass extends FirebaseMessagingService {
    private final String CHANNEL_ID="channel_id";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        String title = message.getData().get("title");
        String messageBody = message.getData().get("message");
        Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int notificationId= new Random().nextInt();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager);
        }
//        PendingIntent intent1 = PendingIntent.getActivities(this,0,new Intent[]{intent},PendingIntent.FLAG_UPDATE_CURRENT);
       PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification notification ;

        notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();



        manager.notify(notificationId,notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager manager) {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"channelName",NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(" My description");
        channel.enableLights(true);
        channel.setLightColor(Color.WHITE);
        manager.createNotificationChannel( channel);
    }
}
