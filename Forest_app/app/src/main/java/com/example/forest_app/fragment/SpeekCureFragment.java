package com.example.forest_app.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.ResponseForm;
import com.example.forest_app.utils.ImageLoader;
import com.example.forest_app.utils.LocalDatabase;
import com.example.forest_app.utils.TTSManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpeekCureFragment extends Fragment {

    private ApiManager apiManager = new ApiManager();
    private TTSManager tts = TTSManager.getInstance(getContext());
    private LocalDatabase ldb = LocalDatabase.getInstance(getContext());
    private String answer, imgData;
    private LinearLayout layout;
    private Button nextButton;
    public SpeekCureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speek_cure, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initalize();

        refresh();
    }

    private void initalize(){
        layout = getView().findViewById(R.id.speek_layout);
        nextButton = getView().findViewById(R.id.speek_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    private void refresh(){
        answer = null;
        imgData = null;
        layout.removeAllViews();
        trySpeek();
    }

    private void trySpeek(){
        Call<ResponseForm> call = apiManager.getApiService().trySpeech(ldb.getAuthForm("test-token"));
        call.enqueue(new Callback<ResponseForm>() {
            @Override
            public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                if(response.isSuccessful()){
                    ResponseForm form = response.body();
                    switch(form.getResult()){
                        case "data:null": break;
                        case "data:img": imgData = form.getData(); break;
                        default: // error log
                    }
                    answer = form.getMessage();
                    constructLayout();
                }
                else{
                    Log.e("SpeekCureFragment", "http fail code: "+response.code()+"\n"+
                            "request info: "+call.request());
                    noInternet();
                }
            }

            @Override
            public void onFailure(Call<ResponseForm> call, Throwable t) {
                Log.e("SpeekCureFragment", "network error: "+t.getMessage()+"\n"+
                        "request info: "+call.request());
                noInternet();
            }
        });
    }

    private void constructLayout(){
        if(imgData == null){
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(300, 100));
            textView.setText(answer);
            textView.setTextSize(24);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.rgb(226, 226, 226));
            layout.addView(textView);
        }
        else{
            ImageView imageView = new ImageView(getContext());
            Bitmap bm = ImageLoader.convertToBitMap(imgData);
            imageView.setImageBitmap(bm);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
            layout.addView(imageView);
        }
    }

    private void noInternet(){
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        textView.setText("서버에 연결할 수 없습니다.");
        textView.setTextSize(24);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.rgb(226, 226, 226));
        layout.addView(textView);
    }
}