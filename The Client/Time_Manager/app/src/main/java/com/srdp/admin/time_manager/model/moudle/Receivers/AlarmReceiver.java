package com.srdp.admin.time_manager.model.moudle.Receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationChannel notificationChannel;
    private Notification notification;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    private String channelId;
    private String channelName;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        channelId = "Alarm";
        channelName = "AlarmChannel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(context);
        String name = intent.getStringExtra("Name");
<<<<<<< HEAD
        String date = intent.getStringExtra("Date");
        notificationBuilder.setContentTitle("待办事务提醒");
        notificationBuilder.setContentText("您有一项新的事务正等待处理：\n  " + name + "将于" + date + "开始进行");
=======
        String date = intent.getStringExtra("Time");
        notificationBuilder.setContentTitle("待办事务提醒");
        notificationBuilder.setContentText("您有一项新的事务:" + name + "将于" + date + "开始");
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
        notificationBuilder.setSmallIcon(android.support.v4.R.drawable.notification_template_icon_bg);
        notificationBuilder.setWhen(System.currentTimeMillis());
        notification = notificationBuilder.build();
        notificationManager.notify(0,notification);
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not yet implemented");
=======
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
    }
}
