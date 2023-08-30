package fr.cnam.application_cours_v1.Services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fr.cnam.application_cours_v1.Fragments.Fragment3;
import fr.cnam.application_cours_v1.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MessengerIntentService extends IntentService {

    public static final int MSG_TOAST_CODE = 1;
    public static final int MSG_NOTIF_CODE = 2;
    private final String LOG_TAG = "MessengerIntentService call";
    private boolean hadWait;
    Messenger responseMessenger;

    public MessengerIntentService() {
        super("MessengerIntentService");
    }

    public class ReceivingHandler extends Handler {

        private Context applicationContext;

        public ReceivingHandler(Context context){
            applicationContext = context.getApplicationContext();
        }



        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int type = msg.what;
            if(type == MSG_TOAST_CODE){
                Log.i("MessengerIntentService call","toast code message received");

                hadWait = false;
                startWaiting();
                while(!hadWait){
                    Log.i(LOG_TAG,"waiting...");
                }
                Toast.makeText(applicationContext,"toast from intent service", Toast.LENGTH_LONG).show();
                Log.i(LOG_TAG,"handle message _ current thread" + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
                Button button = (Button) msg.obj;
                Log.i(LOG_TAG,"button text: " + button.getText());
                button.setEnabled(true);
                responseMessenger = msg.replyTo;
                //mainActivity.findViewById(R.id.toastIntentServiceButton).setEnabled(false);
            }
            else if (type == MSG_NOTIF_CODE){
                Log.i(LOG_TAG,"handle message _ current thread =" + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
                startNotification();

                responseMessenger = msg.replyTo;
                Message message = new Message();
                message.what = Fragment3.whatResponse;
                startWaiting(message);
            }

        }
    }

    Messenger serviceMessenger ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG,"service onbind called");
        serviceMessenger = new Messenger(new ReceivingHandler(getApplicationContext()));
        Toast.makeText(getApplicationContext(),"Service bound",Toast.LENGTH_LONG).show();
        Log.i(LOG_TAG,"onBind _ current thread" + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
        return serviceMessenger.getBinder();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(LOG_TAG,"onHandle intent called - current thread =" + Thread.currentThread().toString() + " id = " + Thread.currentThread().getId());
    }

    public void startNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = getString(R.string.app_name);
        NotificationChannel notificationChannel = new NotificationChannel(channelId,channelId,NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription(channelId);
        notificationChannel.setSound(null,null);
        Log.i(LOG_TAG,"onBind _ current thread" + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
        notificationManager.createNotificationChannel(notificationChannel);
        Notification notification = new Notification.Builder(this, channelId)
                .setAutoCancel(true)
                .setContentText("ForegroundService")
                .setContentTitle("exemple of foreground service")
                .setSmallIcon(R.drawable.bob_icon_72)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build();
        startForeground(999,notification);
        startWaiting();



    }

    public void startWaiting(){
        new Thread(){
            @Override
            public void run() {
                synchronized (this){
                    try {
                        Log.i(LOG_TAG,"wait started");
                        wait(5000);
                        Log.i(LOG_TAG,"wait finished");
                        hadWait = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }
    public void startWaiting(Message msg){
        new Thread(){
            @Override
            public void run() {
                synchronized (this){
                    try {
                        Log.i(LOG_TAG,"wait started");
                        wait(5000);
                        Log.i(LOG_TAG,"wait finished");
                        hadWait = true;
                        responseMessenger.send(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

}