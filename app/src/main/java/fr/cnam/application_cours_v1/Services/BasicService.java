package fr.cnam.application_cours_v1.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class BasicService extends Service {

    private String LOG_TAG = "BasicService call";
    private int CURRENT_SDK = Build.VERSION.SDK_INT;
    private String device = Build.DEVICE;
    //private String serial = Build.getSerial();
    private String model = Build.BRAND + " " + Build.MODEL;


    public BasicService() {
    }
    /*
     * créer un service de téléchargement de fichier avec limiteur de bande passante
     * */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG,"OnStartCommand called");
        Log.i(LOG_TAG, "current SDK: "+ CURRENT_SDK);
        Log.i(LOG_TAG,"current thread onStartCommand:" + Thread.currentThread());
        Thread thread = new Thread() {

            @Override
            public void run() {

                Log.i(LOG_TAG,"startId = " + startId);
                Bundle receivedExtras = intent.getExtras();

                Log.i(LOG_TAG, "bundle extra 2: " + receivedExtras.getString("BUNDLE_ATTACHMENT_2"));
                Log.i(LOG_TAG,"current thread first run:" + Thread.currentThread());


                    Log.i(LOG_TAG,"version under target (<=29");

                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BasicService.this, "your " + model +" - " + device +
                                    "\nruns on API: " + CURRENT_SDK, Toast.LENGTH_LONG).show();
                        }
                    });

                    synchronized (this){
                        try{
                            wait(3000);
                        }catch(Exception e){
                            Toast.makeText(BasicService.this,e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    Log.i(LOG_TAG, "stopSelf reached");

                    stopSelf(startId);
                }
            };


        thread.start();

        Log.i(LOG_TAG,"thread.start reached");

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"service destroyed");
        Toast.makeText(getApplicationContext(), "service destroyed", Toast.LENGTH_LONG).show();
    }
}