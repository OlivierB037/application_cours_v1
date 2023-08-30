package fr.cnam.application_cours_v1.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import fr.cnam.application_cours_v1.Fragments.ThreadFragment;

public class DownloadService extends Service {
    private IBinder BinderInterface = new ServiceBinder();
    private String TAG = "DownloadService call";
    Handler mHandler;
    Message msg;
    Message progressMsg;
    public class ServiceBinder extends Binder {
        public DownloadService getBoundService(){return DownloadService.this; }
    }


    public DownloadService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        //mHandler = new Handler()

        return BinderInterface;
    }


//    public Handler getmHandler(){
//        return mHandler;
//    }

    public void fileDownload(String urlPath, Handler handler){
        /*
        handler pour afficher nom fichier et gérer progressBar
         */


        new Thread((Runnable) () -> {
            InputStream inputStream;
            FileOutputStream fos;
            InputStreamReader inputStreamReader;


            try {
                URL url = new URL(urlPath);
                File outputFile = new File(Environment.getExternalStorageDirectory() + "/Download",url.getFile());
                if(outputFile.exists()){
                    Log.i(TAG,"file does exists");
                    outputFile.delete();
                }
                Log.i(TAG,"file name: " + url.getPath());
                inputStream = url.openConnection().getInputStream();
                Log.i(TAG,"first line passed");
                inputStreamReader = new InputStreamReader(inputStream);
                fos = new FileOutputStream(outputFile.getPath());

                Log.i(TAG,"input test: " + inputStreamReader.read());

                msg = new Message();
                msg.setTarget(handler);
                msg.what = ThreadFragment.FILE_SIZE_MESSAGE_CODE;
                int size = 0;
                while(inputStream.read() != -1){
                    size++;
                }
                Log.i(TAG,"file size is:" + size + " octets");
                msg.arg1 = size;
                msg.sendToTarget();

                int next = -1;
                while((next = inputStreamReader.read()) != -1){
                    progressMsg = Message.obtain(handler,ThreadFragment.PROGRESS_MESSAGE_CODE);
                    Log.i(TAG,"first while passed");
                    fos.write(next);
                    progressMsg.sendToTarget();
                    Log.i(TAG,"download bits:" + (next*4));
                }

            }catch (Exception e){
                Log.i(TAG, e.toString());

            }
        }).start();

    }




}