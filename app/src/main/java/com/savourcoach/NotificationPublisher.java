package com.savourcoach;

/**
 * Created by Suchi on 1/8/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

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
            String[] notificationMessages = context.getResources().getStringArray(R.array.notification_messages);
            int randNo = randInt(0,notificationMessages.length-1);

            notificationMessages.clone();
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            notificationManager.notify(id, getNotification(context,notificationMessages[randNo]));
        }else{
            Log.d(TAG,"Reminder is off onReceive");
            Toast.makeText(context,"Reminder is turned off..",Toast.LENGTH_SHORT).show();

        }
    }

    private Notification getNotification(Context context,String content) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setSound(soundUri);
        return builder.build();
    }
    public int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}