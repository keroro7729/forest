package com.example.forest_app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.Text_1_Img_4_Form;
import com.example.forest_app.form.Text_4_Img_1_Form;
import com.example.forest_app.utils.ImageLoader;
import com.example.forest_app.utils.LocalDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordImgCureFragment extends Fragment {

    private final ApiManager apiManager = new ApiManager();
    private LocalDatabase ldb;
    private LinearLayout layoutRow1, layoutRow2, layoutRow3;

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

        if(Math.random() < 0.5)
            findWordByImg();
        else findImgByWord();
    }

    private void initialize(){
        ldb = LocalDatabase.getInstance(getContext());
        layoutRow1 = getView().findViewById(R.id.layout_row1);
        layoutRow2 = getView().findViewById(R.id.layout_row2);
        layoutRow3 = getView().findViewById(R.id.layout_row3);
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
                }
            }

            @Override
            public void onFailure(Call<Text_4_Img_1_Form> call, Throwable t) {
                Log.e("findWordByImg", "Network error: "+t.getMessage()+"\n"
                    +"request info: "+call.request());
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
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(imageParams);
        // should add click listener

        layoutRow1.addView(imageView);

        // should suffle
        for(int i=0; i<form.getTexts().size(); i++){
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
            // should add click listener

            switch(i){
                case 0:
                case 1: layoutRow2.addView(textView); break;
                case 2:
                case 3: layoutRow3.addView(textView); break;
                default: //error log
            }
        }
    }

    private void constructLayout(Text_1_Img_4_Form form){
        TextView textView = new TextView(getContext());
        textView.setText(form.getText());
        textView.setTextSize(16);
        textView.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(textParams);
        // should add click listener

        layoutRow1.addView(textView);

        // should suffle
        for(int i=0; i<form.getImgDatum().size(); i++){
            String imgData = form.getImgDatum().get(i);
            ImageView imageView = new ImageView(getContext());
            Bitmap bm = ImageLoader.convertToBitMap(imgData);
            imageView.setImageBitmap(bm);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            imageView.setLayoutParams(imageParams);
            // should add click listener

            switch(i){
                case 0:
                case 1: layoutRow2.addView(imageView); break;
                case 2:
                case 3: layoutRow3.addView(imageView); break;
                default: //error log
            }
        }
    }
}