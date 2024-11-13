package com.example.forest_app.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forest_app.R;

public class AlarmFragment extends Fragment {

    private Button timezone, addBtn;
    private TextView time, name;
    private LinearLayout layout;
    public AlarmFragment(){}
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View v){
        timezone = v.findViewById(R.id.timezone_btn);
        time = v.findViewById(R.id.time_text_input);
        addBtn = v.findViewById(R.id.set_alarm_btn);
        name = v.findViewById(R.id.alarmname_text_input);
        layout = v.findViewById(R.id.alarm_layout);

        timezone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tz = timezone.getText().toString();
                if(tz.equals("오전")){
                    timezone.setText("오후");
                }
                else{
                    timezone.setText("오전");
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alarmStr = timezone.getText().toString() + " ";
                alarmStr += time.getText().toString() + " ";
                alarmStr += name.getText().toString();
                createNewAlarm(alarmStr);
            }
        });
    }

    private void createNewAlarm(String alarmStr){
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // 가중치
        ));
        textView.setText(alarmStr);

        Button button = new Button(getContext());
        button.setLayoutParams(new LinearLayout.LayoutParams(
                100,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        button.setText("X");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(linearLayout);
            }
        });

        linearLayout.addView(textView);
        linearLayout.addView(button);

        layout.addView(linearLayout);
    }
}