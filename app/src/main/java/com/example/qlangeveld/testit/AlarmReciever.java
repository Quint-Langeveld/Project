package com.example.qlangeveld.testit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import static com.example.qlangeveld.testit.App.CHANNEL_1_ID;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = (String) intent.getSerializableExtra("title");

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, InputActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.winner)
                        .setContentTitle("Test it!")
                        .setContentText(title)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setVibrate(new long[] { 500, 500})
                        .setContentIntent(pendingIntent)
                        .build();


        notificationManager.notify(1, notification);


//
//        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
//                context).setSmallIcon(R.drawable.applogo)
//                .setContentTitle("Alarm Fired")
//                .setContentText("Events to be Performed").setSound(alarmSound)
//                .setAutoCancel(true).setWhen(when)
//                .setContentIntent(pendingIntent)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//        notificationManager.notify(MID, mNotifyBuilder.build());
//        MID++;

    }


}
