package com.fratelloinnotech.blooddonors.Beans;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.fratelloinnotech.blooddonors.Map.MainMapActivity;

/**
 * Created by fit on 19-01-2019.
 */

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Donation Time !", Toast.LENGTH_LONG).show();

        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, MainMapActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(context)
                .setTicker("NeedBlood -- Donation Time")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Donation Time")
                .setContentText("it's time to go to the nearest hospital and give your blood someone in need :D")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);


    }

}
