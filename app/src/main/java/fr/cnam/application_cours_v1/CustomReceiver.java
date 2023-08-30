package fr.cnam.application_cours_v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.cnam.application_cours_v1.Services.BroadcastIntentService;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("customReceiver call", "onReceive called intent action =" + intent.getAction());
        Log.i("customReceiver call", "onReeive: current thread = " + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
        Intent notifIntent = new Intent(context, BroadcastIntentService.class);
        context.startService(notifIntent);
    }
}