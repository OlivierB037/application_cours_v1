package fr.cnam.application_cours_v1.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import fr.cnam.application_cours_v1.DrawView;
import fr.cnam.application_cours_v1.R;


public class Fragment1 extends Fragment implements View.OnTouchListener {

    private Bundle args;

    public Fragment1() {

    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.println(Log.WARN,"onviewcreated: ", "déclenché");

        getView().setOnTouchListener(this);
        Button nextButton = (Button) getView().findViewById(R.id.buttonnext);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Fragment1.this)
                        .navigate(R.id.Fragment1toFragment2);
//                mClickListener.buttonClicked();
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.println(Log.WARN,"oncreateView", "déclenché");


        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("Fragment1 call", "onTouchEvent déclenché");

        // writing touch data

        TextView textView1 = (TextView) getActivity().findViewById(R.id.textview1);
        textView1.setText("event type : " + MotionEvent.actionToString(event.getActionMasked()));
        TextView textView2 = (TextView) getActivity().findViewById(R.id.textview2);
        textView2.setText("pointer count : " + event.getPointerCount());

        //drawing

        DrawView drawView = (DrawView) getView().findViewById(R.id.draw);
        

        if(event.getPointerCount() == 2) {
            Log.i("function state", "pointercount = 2");
            int pointerIndex = 0;
            int pointerIndexEnd = 1;

            drawView.drawALine(event.getX(pointerIndex), event.getY(pointerIndex), event.getX(pointerIndexEnd), event.getY(pointerIndexEnd));

            /*if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.i("function state", "action = ACTION_DOWN");


            }
            else if(event.getAction() == MotionEvent.ACTION_MOVE){

                while (event.getAction() != MotionEvent.ACTION_UP && event.getPointerCount() > 1) {

                    drawView.drawALine(event.getX(pointerIndex), event.getY(pointerIndex), event.getX(pointerIndexEnd), event.getY(pointerIndexEnd));
                }
                drawView.touched = false;
            }*/

        }
        //if(event.getAction() == MotionEvent.A)
            //https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures

        return true;
    }
}