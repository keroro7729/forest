package com.example.forest_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.forest_app.R;

public class MyListFragment extends Fragment {

    private Button wordImgButton, calculatingButton, listeningButton, button4, button5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_list, container, false);
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

        listeningButton = view.findViewById(R.id.listening_cure_button);
        listeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.listening_cure);
            }
        });
    }

}