package fr.cnam.application_cours_v1.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BindService extends Service {
    private final IBinder BinderInterface = new ServiceBinder();
    private final String LOG_TAG = "BoundService call";

    public BindService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "onBind called");
        Toast.makeText(getApplicationContext(),"service is bind", Toast.LENGTH_LONG).show();
        return BinderInterface;
    }



    public class ServiceBinder extends Binder {
        public BindService getBoundservice(){return BindService.this;}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"service destroyed");
    }

    public void stopBindService(){
        stopSelf();
    }

    public void doToast(String text){
        Log.i(LOG_TAG, "doToast called");
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();


//        Handler mHandler = new Handler(Looper.getMainLooper());
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
//            }
//        });
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
//                mHandler.post(this);
//            }
//        };
    }

}