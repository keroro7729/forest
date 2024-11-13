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
import com.example.forest_app.utils.LocalDatabase;
import com.example.forest_app.utils.TTSManager;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ExpressionFragment extends Fragment {

    private List<Button> buttons;
    private String customExpressions;
    private TextView expressionText;
    private Button addExpressionBtn;
    private LinearLayout layout;
    private TTSManager tts;
    private LocalDatabase ldb;
    public ExpressionFragment(){}
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        buttons = new ArrayList<>();
        tts = TTSManager.getInstance(context);
        ldb = LocalDatabase.getInstance(context);
        customExpressions = ldb.getString("expressions", "");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expression, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View v){
        buttons.add((Button)v.findViewById(R.id.ex_bathroom_btn));
        buttons.add((Button)v.findViewById(R.id.ex_eat_btn));
        buttons.add((Button)v.findViewById(R.id.ex_give_me_btn));
        buttons.add((Button)v.findViewById(R.id.ex_good_btn));
        buttons.add((Button)v.findViewById(R.id.ex_gowithme_btn));
        buttons.add((Button)v.findViewById(R.id.ex_hello_btn));
        buttons.add((Button)v.findViewById(R.id.ex_no_btn));
        buttons.add((Button)v.findViewById(R.id.ex_sick_btn));
        buttons.add((Button)v.findViewById(R.id.ex_stop_btn));
        buttons.add((Button)v.findViewById(R.id.ex_thirsty_btn));
        buttons.add((Button)v.findViewById(R.id.ex_yes_btn));

        for(Button btn : buttons){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ex = btn.getText().toString();
                    tts.speak(ex);
                }
            });
        }

        expressionText = v.findViewById(R.id.expression_text_input);
        addExpressionBtn = v.findViewById(R.id.add_expression_btn);
        layout = v.findViewById(R.id.expression_layout);
        addExpressionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expression = expressionText.getText().toString();
                customExpressions += " "+expression;
                ldb.putString("expressions", customExpressions);
            }
        });
        StringTokenizer st = new StringTokenizer(customExpressions);
        while(st.hasMoreElements()){
            String expression = st.nextToken();
            // 버튼을 생성하는 코드
            Button newBtn = new Button(getContext());
            newBtn.setText(expression);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            newBtn.setLayoutParams(params);
            layout.addView(newBtn);
        }
    }
}