package com.example.forest_app.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import com.example.forest_app.R;

import java.util.ArrayList;
import java.util.Collections;

public class CalculatingCureFragment extends Fragment {

    private TextView Calquestion;
    private TextView Calquestion1;
    private TextView Calquestion2;
    private Button Calexample;
    private Button Calexample1;
    private Button Calexample2;
    private Button Calexample3;

    private int correctAnswer;
    private int correctCount;

    private int CalrandomNumber1 = (int) (Math.random() * 1001)*100;  // 0 ~ 100
    private int CalrandomNumber2 = (int) (Math.random() * 101)*100;  // 0 ~ 100
    private int CalrandomNumber3 = (int) (Math.random() * 101)*100;  // 0 ~ 100
    private int CalrandomNumber4 = (int) (Math.random() * 101)*100;  // 0 ~ 100
    private int CalrandomNumber5 = (int) (Math.random() * 101)*100;  // 0 ~ 100

    public CalculatingCureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calculating_cure, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initialize();
        refresh();
    }


    private void initialize() {
        View view = getView();
            Calquestion = view.findViewById(R.id.textViewCal);
            Calquestion1 = view.findViewById(R.id.textViewCal1);
            Calquestion2 = view.findViewById(R.id.textViewCal2);
            Calexample = view.findViewById(R.id.buttonCal1);
            Calexample1 = view.findViewById(R.id.buttonCal2);
            Calexample2 = view.findViewById(R.id.buttonCal3);
            Calexample3 = view.findViewById(R.id.buttonCal4);

    }


    @SuppressLint("SetTextI18n")
    private void refresh() {
        CalrandomNumber1 = (int) (Math.random() * 1001)*100;
        CalrandomNumber2 = (int) (Math.random() * 101)*100;
        CalrandomNumber3 = (int) (Math.random() * 101)*100;
        CalrandomNumber4 = (int) (Math.random() * 101)*100;
        CalrandomNumber5 = (int) (Math.random() * 101)*100;

        correctAnswer = CalrandomNumber1 + CalrandomNumber2;


        Calquestion.setText(CalrandomNumber1 + "원 + " + CalrandomNumber2 + "원 = ?원 ");


        ArrayList<Button> buttonList = new ArrayList<>();
        buttonList.add(Calexample);
        buttonList.add(Calexample1);
        buttonList.add(Calexample2);
        buttonList.add(Calexample3);
        Collections.shuffle(buttonList);

        buttonList.get(0).setText(Integer.toString(correctAnswer));
        buttonList.get(1).setText(Integer.toString(CalrandomNumber1 + CalrandomNumber3));
        buttonList.get(2).setText(Integer.toString(CalrandomNumber1 + CalrandomNumber4));
        buttonList.get(3).setText(Integer.toString(CalrandomNumber1 + CalrandomNumber5));

        buttonList.get(0).setOnClickListener(v -> checkAnswer(buttonList.get(0), correctAnswer));
        buttonList.get(1).setOnClickListener(v -> checkAnswer(buttonList.get(1), CalrandomNumber1 + CalrandomNumber3));
        buttonList.get(2).setOnClickListener(v -> checkAnswer(buttonList.get(2), CalrandomNumber1 + CalrandomNumber4));
        buttonList.get(3).setOnClickListener(v -> checkAnswer(buttonList.get(3), CalrandomNumber1 + CalrandomNumber5));

        Calexample.setBackgroundColor(Color.rgb(226, 226, 226));
        Calexample1.setBackgroundColor(Color.rgb(226, 226, 226));
        Calexample2.setBackgroundColor(Color.rgb(226, 226, 226));
        Calexample3.setBackgroundColor(Color.rgb(226, 226, 226));

    }
    @SuppressLint("SetTextI18n")
    private void checkAnswer(Button button, int selectedAnswer) {
        if (selectedAnswer == correctAnswer) {
            Calquestion1.setText("정답입니다!");
            correctCount++;
            Calquestion2.setText("맞은 횟수: " + correctCount);
            refresh();
        } else {
            Calquestion1.setText("오답입니다. 다시 선택하세요.");
            button.setBackgroundColor(Color.RED);
        }
    }
}
