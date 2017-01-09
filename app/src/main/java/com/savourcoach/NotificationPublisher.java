package com.savourcoach;

/**
 * Created by Suchi on 1/8/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationPublisher extends BroadcastReceiver {

    private static final String TAG = "NotificationPublisher";
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    PreferenceManager mPreferenceManager;

    public void onReceive(Context context, Intent intent) {
        mPreferenceManager = new PreferenceManager(context);
        Log.d(TAG,"NotificationPublisher onReceive");
        if(mPreferenceManager.isReminderOn()){
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            notificationManager.notify(id, notification);
        }else{
            Log.d(TAG,"Reminder is off onReceive");
            Toast.makeText(context,"Reminder is turned off..",Toast.LENGTH_SHORT).show();

        }


    }
}