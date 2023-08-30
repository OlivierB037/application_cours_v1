package fr.cnam.application_cours_v1.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BasicIntentService extends IntentService {

    private Intent returnIntent;
    private static final String ACTION_DOWNLOAD = "fr.cnam.application_cours_v1.action.DOWNLOAD";
    private static final String LOG_TAG = "intentService call";
    // TODO: Rename parameters

    public BasicIntentService() {
        super("BasicIntentService");

    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String action = intent.getAction();
            switch (action){
                case ("serviceAction"):{
                    returnIntent = intent;
                    Log.i(LOG_TAG,"uri: " + intent.getData().toString());

                    serviceAction();
                } break;
                case ("toastText"): toastText(intent.getStringExtra("text"));
                break;
                default: Log.i(LOG_TAG, "no action found");
                break;
            }
        }
    }


    public Class<?> returnData(){
        String type = returnIntent.getData().toString();
        Log.i(LOG_TAG, "string class: " + type);
        if(type.contains("class ")){
            type = type.substring(6);
        }
        Log.i(LOG_TAG,"->" + type);

        try {
            return Class.forName(type);
        }catch (ClassNotFoundException e){
            Log.i(LOG_TAG, e.toString());
            return String.class;
        }
    }




    public void serviceAction() {
        try {
            Object returnObject = returnData().newInstance();
            Log.i("service call","object type is: " + returnObject.getClass().getName());
        } catch (IllegalAccessException e) {
            Log.i("service call", e.toString());
            //e.printStackTrace();
        } catch (InstantiationException e) {
            Log.i("service call", e.toString());
            //e.printStackTrace();
        }
        Log.i("downloadIntentService call", "ServiceAction called");
    }

    public void returnRandom(){

    }


    public void toastText(String text) {
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(LOG_TAG,"toast text called");
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });

    }
}