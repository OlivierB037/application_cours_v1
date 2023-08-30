package fr.cnam.application_cours_v1.Fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import fr.cnam.application_cours_v1.DrawView;
import fr.cnam.application_cours_v1.R;


public class SensorsFragment extends Fragment implements SensorEventListener {

    private final String TAG = "SensorsFragment call";

    SensorEventListener sensorListener;
    SensorManager sensorManager;
    private boolean moving;
    private int sensorIndex;
    private int sensorCount = -1;
    private List<Sensor> gyroscopes;
    private float maxSensor;
    float previousX = 0;
    float previousY = 0;

    public SensorsFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sensorManager =(SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorIndex = 0;
        moving = false;
        gyroscopes = null;
        return inflater.inflate(R.layout.fragment_sensors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        DrawView drawView = (DrawView) getActivity().findViewById(R.id.sensorDraw);

        getView().findViewById(R.id.sensorListButton).setOnClickListener(v -> {
            getAllSensorsList();
            //drawView.doAFullCircle(250,350,100);
        });

        getView().findViewById(R.id.sensorButton).setOnClickListener(view1 -> {
            //drawView.doAFullCircle(250,350,100);
            
            if(gyroscopes != null && moving) {
                sensorManager.unregisterListener(this);
                if(sensorIndex == (sensorCount - 1)){
                    Log.i(TAG, "onViewCreated: return to sensorIndex 0");
                    sensorIndex = 0;
                }
                else{
                    sensorIndex++;
                    Log.i(TAG, "onViewCreated: sensorIndex increment: index= " + sensorIndex);
                }
                maxSensor = gyroscopes.get(sensorIndex).getMaximumRange();
            }
            else{
                if (!moving) Log.i(TAG, "onViewCreated: no movementnt listening");
                else Log.i(TAG, "onViewCreated: no gyroscope registered");
            }
        });

        Button moveButton = (Button) getView().findViewById(R.id.moveButton);
        moveButton.setOnClickListener(v -> {
            //drawView.moveCircle(22,92);
            //drawView.moveCircleToPosition(120,90);
            if (!moving) {
                gyroscopes = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
                sensorCount = gyroscopes.size();
                for (Sensor e : gyroscopes) {
                    Log.i(TAG, "onclick moveButton - sensor " + e.getName());
                    Log.i(TAG, "onViewCreated: sensor resolution= " + e.getResolution());
                    Log.i(TAG, "onViewCreated: senor maxValue = " + e.getMaximumRange());
                }
                moveButton.setText("stop moving");
                moving = true;
                maxSensor = gyroscopes.get(sensorIndex).getMaximumRange();
                //sensorManager.registerListener(this, gyroscopes.get(sensorIndex),SensorManager.SENSOR_DELAY_GAME);
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_UI);
            }
            else{
                sensorManager.unregisterListener(this);
                moveButton.setText("start moving");
                moving = false;
            }
        });
    }

    public void getAllSensorsList(){
        SensorManager sensorManager =(SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor e: sensorList) {
            Log.i(TAG,"sensor: " + e.getName());
            Log.i(TAG, "getAllSensorsList: accelerometer string type " + sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).getName());
            Log.i(TAG, "getAllSensorsList: accelerometer type" + Sensor.STRING_TYPE_ACCELEROMETER);
            //if (e.)
        }
    }
    public Bundle moveBall(Sensor sensor){
        Bundle coordinates = new Bundle();
        return coordinates;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {



            float[] difference = {Math.abs((previousX) - (event.values[0])), Math.abs((previousY) - (event.values[1]))};
//            Log.i(TAG, "onSensorChanged: X= " + event.values[0] + " Y= " + event.values[1]);
//            Log.i(TAG, "onSensorChanged: previousX= " + previousX + " previousY " + previousY);
//            Log.i(TAG, "onSensorChanged: differenceX =" + difference[0] + " differenceY " + difference[1]);
            if (difference[0] > (maxSensor / 50) || difference[1] > (maxSensor / 50)) {
                Log.i(TAG, "onSensorChanged: X= " + event.values[0] + " Y= " +
                        event.values[1] + " Z= " + event.values[2]);
                Log.i(TAG, "onSensorChanged: differenceX =" + difference[0] + " differenceY " + difference[1]);
            }
            previousX = event.values[0];
            previousY = event.values[1];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class sensorReading extends AsyncTask<Sensor,DrawView,String>{

        @Override
        protected String doInBackground(Sensor... sensors) {
            return null;
        }

        @Override
        protected void onProgressUpdate(DrawView... values) {
            DrawView drawView = values[0];
            drawView.
        }
    }

}