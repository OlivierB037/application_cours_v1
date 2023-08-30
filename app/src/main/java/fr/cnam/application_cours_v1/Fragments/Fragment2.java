package fr.cnam.application_cours_v1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import fr.cnam.application_cours_v1.R;


public class Fragment2 extends Fragment {





    public Fragment2() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Fragment 2 call this = ", this.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton imageButton = (ImageButton) getView().findViewById(R.id.imagebutt);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"clicked", Toast.LENGTH_SHORT).show();

            }
        });

        Button createButton = (Button) getView().findViewById(R.id.createFile);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT,createIntent);
                createIntent.addCategory(Intent.CATEGORY_OPENABLE);
                createIntent.setType("text/plain");
                createIntent.putExtra(Intent.EXTRA_TITLE,"essai.txt");



                startActivity(chooserIntent);

            }
        });

        Button nextButton = (Button) getView().findViewById(R.id.buttonnext2);
        nextButton.setOnClickListener((v)->{
            nextFragment();
        });
        Button menuButton = (Button) getView().findViewById(R.id.frag2menuButton);
        menuButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(Fragment2.this)
                    .navigate(R.id.Fragment2toMenu);
        });

    }

    public void nextFragment(){
        NavHostFragment.findNavController(Fragment2.this)
                .navigate(R.id.Fragment2toFragment3);
    }


}