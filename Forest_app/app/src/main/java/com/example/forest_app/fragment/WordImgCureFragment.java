package com.example.forest_app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.Text_1_Img_4_Form;
import com.example.forest_app.form.Text_4_Img_1_Form;
import com.example.forest_app.utils.ImageLoader;
import com.example.forest_app.utils.LocalDatabase;
import com.example.forest_app.utils.Shuffler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordImgCureFragment extends Fragment {

    private final ApiManager apiManager = new ApiManager();
    private LocalDatabase ldb;
    private LinearLayout layoutRow1, layoutRow2, layoutRow3;
    //private Button select1, select2, select3, select4;
    //private ImageView imageView;

    public WordImgCureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_word_img_cure, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        refresh();
    }

    private void refresh(){
        layoutRow1.removeAllViews();
        layoutRow2.removeAllViews();
        layoutRow3.removeAllViews();

        if(Math.random() < 0.5)
            findWordByImg();
        else findImgByWord();
    }

    private void initialize(){
        ldb = LocalDatabase.getInstance(getContext());
        View view = getView();
        layoutRow1 = view.findViewById(R.id.layout_row1);
        layoutRow2 = view.findViewById(R.id.layout_row2);
        layoutRow3 = view.findViewById(R.id.layout_row3);
        /*
        imageView = view.findViewById(R.id.imageView);
        select1 = view.findViewById(R.id.select_button1);
        select2 = view.findViewById(R.id.select_button2);
        select3 = view.findViewById(R.id.select_button3);
        select4 = view.findViewById(R.id.select_button4);
        */
    }

    private void findWordByImg(){
        Call<Text_4_Img_1_Form> call = apiManager.getApiService()
                .findWordByImg(ldb.getAuthForm("test-token"));
        call.enqueue(new Callback<Text_4_Img_1_Form>() {
            @Override
            public void onResponse(Call<Text_4_Img_1_Form> call, Response<Text_4_Img_1_Form> response) {
                if(response.isSuccessful()){
                    // build fragment layout by response form
                    constructLayout(response.body());
                }
                else{
                    Log.e("findWordByImg", "http fail code: "+response.code()+"\n"
                        +"request info: "+call.request());
                    noInternet();
                }
            }

            @Override
            public void onFailure(Call<Text_4_Img_1_Form> call, Throwable t) {
                Log.e("findWordByImg", "Network error: "+t.getMessage()+"\n"
                    +"request info: "+call.request());
                noInternet();
            }
        });
    }

    private void findImgByWord(){
        Call<Text_1_Img_4_Form> call = apiManager.getApiService()
                .findImgByWord(ldb.getAuthForm("test-token"));
        call.enqueue(new Callback<Text_1_Img_4_Form>() {
            @Override
            public void onResponse(Call<Text_1_Img_4_Form> call, Response<Text_1_Img_4_Form> response) {
                if(response.isSuccessful()){
                    // build fragment layout by response form
                    constructLayout(response.body());
                }
                else{
                    Log.e("findImgByWord", "http fail code: "+response.code()+"\n"
                            +"request info: "+call.request());
                }
            }

            @Override
            public void onFailure(Call<Text_1_Img_4_Form> call, Throwable t) {
                Log.e("findImgByWord", "Network error: "+t.getMessage()+"\n"
                        +"request info: "+call.request());
            }
        });
    }

    private void constructLayout(Text_4_Img_1_Form form){
        ImageView imageView = new ImageView(getContext());
        Bitmap bm = ImageLoader.convertToBitMap(form.getImgData());
        imageView.setImageBitmap(bm);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(600, 600);
        imageView.setLayoutParams(imageParams);

        layoutRow1.addView(imageView);

        int count = 0;
        for(int i : Shuffler.get()){
            String text = form.getTexts().get(i);
            TextView textView = new TextView(getContext());
            textView.setText(text);
            textView.setTextSize(16);
            textView.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(textParams);

            if(i == 0){ // when correct answer
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "정답입니다!", Toast.LENGTH_LONG).show();
                        refresh();
                    }
                });
            }
            else{ // when wrong answer
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "오답입니다! 다시 선택해보세요!", Toast.LENGTH_LONG).show();
                        textView.setBackgroundColor(Color.rgb(223, 100, 100));
                    }
                });
            }

            switch(count){
                case 0:
                case 1: layoutRow2.addView(textView); break;
                case 2:
                case 3: layoutRow3.addView(textView); break;
                default: //error log
            } count++;
        }
    }

    private void constructLayout(Text_1_Img_4_Form form){
        // 강조 해주라
        TextView textView = new TextView(getContext());
        textView.setText(form.getText());
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(textParams);

        layoutRow1.addView(textView);

        int count = 0;
        for(int i : Shuffler.get()){
            String imgData = form.getImgDatum().get(i);
            ImageView imageView = new ImageView(getContext());
            Bitmap bm = ImageLoader.convertToBitMap(imgData);
            imageView.setImageBitmap(bm);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    300, 300
            );
            imageView.setLayoutParams(imageParams);

            if(i == 0){ // when correct answer
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "정답입니다!", Toast.LENGTH_LONG).show();
                        refresh();
                    }
                });
            }
            else{ // when wrong answer
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "오답입니다! 다시 선택해보세요!", Toast.LENGTH_LONG).show();
                        imageView.setBackgroundColor(Color.rgb(223, 100, 100));
                        imageView.setAlpha(0.5f);
                    }
                });
            }

            switch(count){
                case 0:
                case 1: layoutRow2.addView(imageView); break;
                case 2:
                case 3: layoutRow3.addView(imageView); break;
                default: //error log
            } count++;
        }
    }

    private void noInternet(){
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.scinec);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                500, 500
        );
        imageView.setLayoutParams(imageParams);
        layoutRow1.addView(imageView);

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(16, 16, 16, 16);  // 여백 설정

        textView.setLayoutParams(params);
        textView.setText("서버에 접속 할 수 없습니다");    // 제목 문구 설정
        textView.setTextSize(24);                     // 텍스트 크기 설정
        textView.setTypeface(Typeface.DEFAULT_BOLD);  // 텍스트 굵기 설정
        textView.setTextColor(Color.BLACK);           // 텍스트 색상 설정
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.rgb(226, 226, 226));
        layoutRow2.addView(textView);
    }
}