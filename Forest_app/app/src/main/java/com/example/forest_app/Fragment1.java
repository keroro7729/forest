package com.example.forest_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);


        ImageView imageView = view.findViewById(R.id.imageView1);

        imageView.setOnClickListener(v -> {

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.navigation_lists);
        });

        return view;
    }
}