package fr.cnam.application_cours_v1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import fr.cnam.application_cours_v1.R;

public class MenuFragment extends Fragment {


    public MenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button scrollButton = (Button) getView().findViewById(R.id.textButton);
        scrollButton.setOnClickListener(v -> {
            TextView scrollText = (TextView) getView().findViewById(R.id.scrollTextview);
            switch(scrollText.getVisibility()) {
                case View.GONE: scrollText.setVisibility(View.VISIBLE);
                break;
                case View.VISIBLE: scrollText.setVisibility(View.GONE);
                break;
                default: scrollText.setVisibility(View.GONE);
                break;
            }
        });

        Button frag1button = (Button) getView().findViewById(R.id.frag1button);
        frag1button.setOnClickListener(v -> {
            NavHostFragment.findNavController(MenuFragment.this)
                    .navigate(R.id.menuToFragment1);

        });

        Button frag2button = (Button) getView().findViewById(R.id.frag2button);
        frag2button.setOnClickListener(v -> {
            NavHostFragment.findNavController(MenuFragment.this)
                    .navigate(R.id.menuToFragment2);
        });

        Button frag3button = (Button) getView().findViewById(R.id.frag3button);
        frag3button.setOnClickListener(v -> {
            NavHostFragment.findNavController(MenuFragment.this)
                    .navigate(R.id.menuToFragment3);
        });

        Button frag4button = (Button) getView().findViewById(R.id.frag4Button);
        frag4button.setOnClickListener(v -> {
            NavHostFragment.findNavController(MenuFragment.this)
                    .navigate(R.id.menuToFragment4);
        });
        getView().findViewById(R.id.frag5button).setOnClickListener(v -> {
            NavHostFragment.findNavController(MenuFragment.this)
                    .navigate(R.id.menuToFragment5);
        });
        getView().findViewById(R.id.frag6button).setOnClickListener(v -> {
            NavHostFragment.findNavController(MenuFragment.this)
                    .navigate(R.id.menuToFragment6);
        });
    }
}