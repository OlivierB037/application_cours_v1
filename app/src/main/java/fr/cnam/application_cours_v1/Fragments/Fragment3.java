package fr.cnam.application_cours_v1.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

import fr.cnam.application_cours_v1.Services.BindService;
import fr.cnam.application_cours_v1.Services.BindService.ServiceBinder;
import fr.cnam.application_cours_v1.Services.MessengerIntentService;
import fr.cnam.application_cours_v1.R;
import fr.cnam.application_cours_v1.Services.BasicIntentService;
import fr.cnam.application_cours_v1.Services.BasicService;


public class Fragment3 extends Fragment {
    private final String LOG_TAG = "Fragment3 call";
    static final int RESPONSE_CODE = 3;
    public static int whatResponse= 12;

    public static  class MyHandler extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);



        }
    }

    public Fragment3() {

    }

    /* services
     *https://www.sanfoundry.com/java-android-program-download-file-service/
     *https://stackoverflow.com/questions/525204/android-download-intent
     * créer un service de téléchargement de fichier avec limiteur de bande passante
     * */



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        ************************* BASIC SERVICE ***********************************************
         */

        Button basicButton = (Button) getView().findViewById(R.id.basicButton);
        basicButton.setOnClickListener(v -> {
            Intent toastIntent = new Intent(getContext(), BasicService.class);

            Bundle stringExtra = new Bundle();
            stringExtra.putString("BUNDLE_ATTACHMENT_1","bonjour du fragment 3");
            stringExtra.putString("BUNDLE_ATTACHMENT_2", "second attachment");
            toastIntent.putExtras(stringExtra);
            toastIntent.putExtra("STRING_EXTRA","string added in extra");
            getContext().startService(toastIntent);
        });

        /*
        *************************** INTENT SERVICE ******************************
         */



        Button toastButton = (Button) getView().findViewById(R.id.intentServiceToastButton);
        toastButton.setOnClickListener(v -> {

            Intent intentService = new Intent(getContext(), BasicIntentService.class);
            intentService.putExtra("text", "Bonjour de IntentService");
            intentService.setAction("toastText");
            getContext().startService(intentService);
        });



        Button intentButton = (Button) getView().findViewById(R.id.intentServiceLogButton);
        intentButton.setOnClickListener(v -> {
            Intent intentData = new Intent();
            File fileData = new File(Environment.getExternalStorageDirectory().getPath() + "/test.dot");

            Uri uri = Uri.parse(File.class.toString());
            Intent intentService = new Intent("serviceAction",uri,getContext(), BasicIntentService.class);
            intentService.setAction("serviceAction");
            intentService.putExtra("filePath", fileData.getPath());

            getContext().startService(intentService);

        });


        Button boundButton = (Button) getView().findViewById(R.id.boundButton);
        boundButton.setOnClickListener(v -> {
            if(boundService == null) {
                Log.i("Fragment3 call Button clicked","bound service is null _ current thread= " + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
                startBoundService();
                boundButton.setText("Stop Service");
            }
            else{
                stopBoundService();
                boundButton.setText("Start service");
            }
        });

        Button bindToastButton = (Button) getView().findViewById(R.id.bindToastButton);
        bindToastButton.setOnClickListener(v -> {
            toastService();
        });
        Button startBoundIntentButton = (Button) getView().findViewById(R.id.bindIntentServiceButton);
        startBoundIntentButton.setOnClickListener(v -> {
            startBoundIntentService();
        });
        Button intentServiceToastButton = (Button) getView().findViewById(R.id.toastIntentServiceButton);
        intentServiceToastButton.setOnClickListener(v ->{
            sendToastMessage();
            intentServiceToastButton.setEnabled(false);
        });
        Button notifButton = (Button) getView().findViewById(R.id.notifIntentServiceButton);
        notifButton.setOnClickListener(view1 -> {
            sendNotifMessage();
        });


    }

    /*
     ************************BOUND SERVICE*****************************
     */


    //private ServiceConnection serviceConnection;
    private BindService boundService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceBinder serviceBinder = (ServiceBinder) service;
            boundService = serviceBinder.getBoundservice();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Log.i(LOG_TAG,"onServiceDisconnected called");
            boundService = null;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.i(LOG_TAG,"onBindingDied called");
        }
    };

    private void startBoundService(){
        Intent boundIntent = new Intent(getContext(), BindService.class);
        getActivity().bindService(boundIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopBoundService(){
        getActivity().unbindService(serviceConnection);
        boundService = null;
    }

    private void toastService(){
        if(boundService != null) {
            boundService.doToast("le port Toaste");
        }
        else {
            Toast.makeText(getContext(),"service is not running", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    *********************** MESSENGER BOUND SERVICE *************************************
     */

    protected Intent messengerIntent;
    private MessengerIntentService myboundIntentService = null;
    private Messenger mService = null;
    private ServiceConnection intentServiceconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    static class ResponseHandler extends Handler{
        private Context applicationContext;

        public ResponseHandler(){

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == whatResponse){
                Log.i("Fragment3 handler call", "response received - current thread = " + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());

            }
        }
    }

    public void startBoundIntentService(){
        messengerIntent = new Intent(getContext(),MessengerIntentService.class);
        getActivity().bindService(messengerIntent,intentServiceconnection,Context.BIND_AUTO_CREATE);
    }

    public void sendToastMessage(){
        Button button = (Button) getView().findViewById(R.id.toastIntentServiceButton);
        Message msg = Message.obtain(null,MessengerIntentService.MSG_TOAST_CODE,button);
        try {
            //msg.sendToTarget();
            Log.i(LOG_TAG,"current thread" + Thread.currentThread().toString());
            msg.replyTo = new Messenger(new ResponseHandler());
            mService.send(msg);
        } catch (RemoteException e) {
            Log.i(LOG_TAG, e.toString());
        }
    }
    public void sendNotifMessage(){
        Message msg = Message.obtain(null,MessengerIntentService.MSG_NOTIF_CODE);
        msg.replyTo = new Messenger(new ResponseHandler());
        try{

            mService.send(msg);
        }catch(Exception e){
            Log.i(LOG_TAG, e.toString());
        }
    }
}