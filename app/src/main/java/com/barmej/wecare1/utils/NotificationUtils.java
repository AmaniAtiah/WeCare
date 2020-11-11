package com.barmej.wecare1.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.barmej.wecare1.activites.MainActivity;
import com.barmej.wecare1.R;
import com.barmej.wecare1.data.entity.UserData;

public class NotificationUtils {

    private static final String HEALTH_CHANNEL_ID = "health";
    private static final int HEALTH_NOTIFICATION_ID = 1;

    public static void createHealthNotificationChannel(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.notification_channel_name);
            String description = context.getString(R.string.notification_channel_description);
            NotificationChannel channel = new NotificationChannel(
                    HEALTH_CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);

        }
    }

    public static void showHealthNotification(Context context, UserData userData) {
        if(userData == null)
            return;

        String notificationTitle = context.getString(R.string.app_name);
        String notificationText = context.getString(
                R.string.Break
        );
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, HEALTH_CHANNEL_ID);
        notificationBuilder.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        notificationBuilder.setContentTitle(notificationTitle);
        notificationBuilder.setContentText(notificationText);
        notificationBuilder.setSmallIcon(R.drawable.ic_stat_name);
        notificationBuilder.setAutoCancel(true);
        Intent intent = new Intent(context, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(HEALTH_NOTIFICATION_ID, notification);

    }
}
