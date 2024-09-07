package com.example.forest_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.forest_app.R;

public class CureListFragment extends Fragment {

    private Button wordImgButton, calculatingButton, listeningButton, recogButton, speekButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cure_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        wordImgButton = view.findViewById(R.id.word_img_cure_button);
        wordImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.word_img_cure);
            }
        });

        calculatingButton = view.findViewById(R.id.calculating_cure_button);
        calculatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.calculate_cure);
            }
        });

        listeningButton = view.findViewById(R.id.listening_cure_button);
        listeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.listening_cure);
            }
        });

        recogButton = view.findViewById(R.id.recog_cure_button);
        recogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.recog_cure);
            }
        });

        speekButton = view.findViewById(R.id.speek_cure_button);
        speekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.speek_cure);
            }
        });
    }

}