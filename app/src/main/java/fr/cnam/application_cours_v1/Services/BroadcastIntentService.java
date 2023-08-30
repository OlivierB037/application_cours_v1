package fr.cnam.application_cours_v1.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import fr.cnam.application_cours_v1.R;


public class BroadcastIntentService extends IntentService {

    private final String CHANNEL_ID = "notifId";
    private final String TAG = "BroadcastIntent Service call";

    public BroadcastIntentService() {
        super("BroadcastIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        NotificationManagerCompat notificationManager = (NotificationManagerCompat) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(NotificationManagerCompat.);
        Log.i(TAG, "onHandleIntent: current thread = " + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
        NotificationManager notificationManager = getSystemService(NotificationManager.class);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.carlo56px)
                .setContentTitle("broadcast notification")
                .setContentText("this is a notification triggered by a broadcast receiver")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID,NotificationManager.IMPORTANCE_HIGH);
            if (notificationChannel == null) Log.i(TAG,"notification channel is null");
            if (notificationManager == null) Log.i(TAG,"notificationManager is null");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Notification notification = notificationBuilder.build();

        notificationManager.notify(123,notification);


    }




}