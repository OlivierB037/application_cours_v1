package fr.cnam.application_cours_v1.Fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

import fr.cnam.application_cours_v1.Services.DownloadService;
import fr.cnam.application_cours_v1.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ThreadFragment extends Fragment {
    ProgressBar bar = null;
    private final String LOG_TAG = "ThreadFragment call";
    private final int PERMISSION_CODE = 38;
    private final String[] urlArray = new String[]{"http://test-debit.free.fr/512.rnd",
        "http://test-debit.free.fr/1024.rnd",
        "http://test-debit.free.fr/2048.rnd",
        "http://test-debit.free.fr/8192.rnd",
        "http://test-debit.free.fr/16384.rnd",
        "http://test-debit.free.fr/32768.rnd",
        "http://test-debit.free.fr/65536.rnd"};

    private String selectedUrl;
    private MHandler mHandler;
    public static final int FILE_SIZE_MESSAGE_CODE = 4;
    public static final int PROGRESS_MESSAGE_CODE = 5;
    DownloadService downloadService = null;
    private boolean connected = false;

    public static class MHandler extends Handler{
        int fileSize;
        ProgressBar bar;
//        Context mcontext;
//        public MHandler(Context context){
//            mcontext = context;
//        }
        WeakReference<ThreadFragment> fragmentReference;
        public MHandler(ThreadFragment threadFragment){
            fragmentReference = new WeakReference<ThreadFragment>(threadFragment);

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case FILE_SIZE_MESSAGE_CODE:{
                    fragmentReference.get().bar.setVisibility(View.VISIBLE);
                    fileSize = msg.arg1 / 1000;
                    String fileString = "file size: " + fileSize + "ko";
                    Log.i("handler call", fileString);
                    Toast.makeText(fragmentReference.get().getContext(), fileString ,Toast.LENGTH_LONG).show();
                    fragmentReference.get().bar.setMax(fileSize);
                }break;
                case PROGRESS_MESSAGE_CODE:{
                    fragmentReference.get().bar.incrementProgressBy(1);
                }break;
            }
        }
    }


    public ThreadFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thread, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHandler = new MHandler(ThreadFragment.this);
        Button bindButton = (Button) getView().findViewById(R.id.downloadBindButton);
        Button middleButton = (Button) getActivity().findViewById(R.id.downloadButton);
        Spinner fileSpinner = (Spinner) getActivity().findViewById(R.id.fileSpinner);
        TextView textView = (TextView) getView().findViewById(R.id.middleTextView);
        bar = (ProgressBar) getView().findViewById(R.id.downloadProgress);

        fileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("selected file size: " + fileSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        bindButton.setOnClickListener(view1 -> {
            if(!connected){
                bindService();
                bindButton.setText("unbind service");
            }
            else{
                unbindDownloadService();
                bindButton.setText("bind service");
            }

        });

        middleButton.setOnClickListener(v -> {
            if(connected) {
                int index = fileSpinner.getSelectedItemPosition();
                Log.i(LOG_TAG, "spinner index: " + index);
                selectedUrl = urlArray[index];
                Log.i(LOG_TAG, "selected url:" + selectedUrl);
                requestFilePermission(PERMISSION_CODE);
            }
            else{
                Toast.makeText(getContext(),"service is not bound",Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_CODE){
            boolean granted = true;
            for (int e:grantResults){
                if (e == PackageManager.PERMISSION_DENIED){
                    Log.i(LOG_TAG,"permission denied");
                    granted = false;
                    break;
                }
            }

            if(granted){
                Log.i(LOG_TAG,"permission granted");
                startDownloadService();
            }
        }
    }

    ServiceConnection sConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Toast.makeText(getContext(),"service bound",Toast.LENGTH_SHORT).show();
            DownloadService.ServiceBinder serviceBinder = (DownloadService.ServiceBinder) iBinder;
            downloadService = serviceBinder.getBoundService();
            if(downloadService != null){
                connected = true;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(getContext(),"service unBind",Toast.LENGTH_SHORT).show();
            downloadService = null;
            connected = false;
        }
    };

    public void requestFilePermission(int permissionCode){
        if (Build.VERSION.SDK_INT < 30) {
            Log.i(LOG_TAG,"sdk number is under 30 -> sdk" + Build.VERSION.SDK_INT);
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, permissionCode);
        }
        else{
            Log.i(LOG_TAG,"sdk number is over 30 -> sdk" + Build.VERSION.SDK_INT);

        }
        Log.i(LOG_TAG,"permission Line passed in overload function");
    }

    public void bindService(){
        Intent downloadIntent = new Intent(getContext(),DownloadService.class);

        getActivity().bindService(downloadIntent,sConn, Context.BIND_AUTO_CREATE);
    }
    public void unbindDownloadService(){
        downloadService.stopSelf();
    }

    public void startDownloadService(){
        Log.i(LOG_TAG,"startDownloadService called");
        downloadService.fileDownload(selectedUrl, mHandler);
    }
}