package fr.cnam.application_cours_v1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BasicReceiver extends BroadcastReceiver {
    private final String TAG = "BasicReceiver call";
    @Override
    public void onReceive(Context context, Intent intent) {
        //switch(intent.getAction()){
        Log.i("basicReceiver call","brodcast triggered");

        Log.i(TAG,"action: " + intent.getAction());
        Toast.makeText(context,"power cable plugged",Toast.LENGTH_SHORT).show();

        PendingResult result = goAsync();

                /*
                *créer un async receiver en utilisant un intent service déclenchant une notification (vérifier appels multiples à un receiver)
                *
                 */
    }
}