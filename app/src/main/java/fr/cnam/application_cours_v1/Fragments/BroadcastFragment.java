package fr.cnam.application_cours_v1.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.cnam.application_cours_v1.BasicReceiver;
import fr.cnam.application_cours_v1.CustomReceiver;
import fr.cnam.application_cours_v1.R;


public class BroadcastFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ACTION_LOCAL_RECEIVER = "My Event";
    private final String TAG = "broadcastFragment call";
    private final String ACTION_NOTIFICATE = "notificate";
    BasicReceiver basicReceiver = null;
    CustomReceiver customReceiver;
    IntentFilter basicFilter = null;
    IntentFilter customFilter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public BroadcastFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static BroadcastFragment newInstance(String param1, String param2) {
//        BroadcastFragment fragment = new BroadcastFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broadcast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG,"onViewCreated called");

        /* ******** basic Broadcast receiver *********/

        basicFilter = new IntentFilter();
        basicFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        basicReceiver = new BasicReceiver();

        /* ******** local Broadcast receiver *********/
        EditText editText = (EditText) getActivity().findViewById(R.id.localReceiverText);
        //editText.setText(" ");
        Log.i(TAG,"edit text value: " + editText.getText());


        /* ********** custom broadcast receiver ***********/
        customFilter = new IntentFilter(ACTION_NOTIFICATE);
        customReceiver = new CustomReceiver();

        Intent localIntent = new Intent(ACTION_LOCAL_RECEIVER);

        getView().findViewById(R.id.localReceiverButton).setOnClickListener(v -> {
            String message = editText.getText().toString();
            localIntent.putExtra("message", message);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(localIntent);
        });

        getView().findViewById(R.id.serviceReceiverButton).setOnClickListener(v -> {
            Log.i(TAG, "button clicked - current thread = " + Thread.currentThread().toString() + " id= " + Thread.currentThread().getId());
            Intent broadcastIntent = new Intent(ACTION_NOTIFICATE);
            getActivity().sendBroadcast(broadcastIntent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(basicReceiver,basicFilter);
        getActivity().registerReceiver(customReceiver,customFilter);
        Log.i(TAG,"onResume called");

    }

    @Override
    public void onPause(){
        super.onPause();
        //getActivity().unregisterReceiver(basicReceiver);
        getActivity().unregisterReceiver(customReceiver);
        Log.i(TAG,"onPause called");
    }
}