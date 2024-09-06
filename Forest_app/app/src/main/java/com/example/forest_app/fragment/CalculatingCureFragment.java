package com.example.forest_app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.widget.TextView;
import android.widget.Button;
import com.example.forest_app.R;


public class CalculatingCureFragment extends Fragment {


    private TextView Calquestion;
    private Button Calexample;
    private Button Calexample1;
    private Button Calexample2;
    private Button Calexample3;

    private int CalrandomNumber1 = (int) (Math.random() * 101);  // 0 ~ 100
    private int CalrandomNumber2 = (int) (Math.random() * 101);  // 0 ~ 100
    private int CalrandomNumber3 = (int) (Math.random() * 101);  // 0 ~ 100
    private int CalrandomNumber4 = (int) (Math.random() * 101);  // 0 ~ 100
    private int CalrandomNumber5 = (int) (Math.random() * 101);  // 0 ~ 100

    public CalculatingCureFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculating_cure, container, false);

        Calquestion = view.findViewById(R.id.textViewCal);
        Calexample = view.findViewById(R.id.buttonCal1);
        Calexample1 = view.findViewById(R.id.buttonCal2);
        Calexample2 = view.findViewById(R.id.buttonCal3);
        Calexample3 = view.findViewById(R.id.buttonCal4);

        Calquestion.setText(CalrandomNumber1+" + "+CalrandomNumber2+" = ? ");
        Calexample.setText(Integer.toString(CalrandomNumber1+CalrandomNumber2));
        Calexample1.setText(Integer.toString(CalrandomNumber1+CalrandomNumber3));
        Calexample2.setText(Integer.toString(CalrandomNumber1+CalrandomNumber4));
        Calexample3.setText(Integer.toString(CalrandomNumber1+CalrandomNumber5));


        return view;
    }
}