package com.example.forest_app.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.Text_4_Img_1_Form;
import com.example.forest_app.utils.ImageLoader;
import com.example.forest_app.utils.LocalDatabase;
import com.example.forest_app.utils.Shuffler;
import com.example.forest_app.utils.TTSManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecogCureFragment extends Fragment {

    private ApiManager apiManager;
    private LocalDatabase ldb = LocalDatabase.getInstance(getContext());
    private TTSManager tts = TTSManager.getInstance(getContext());
    private String answer;

    private ImageView imageView;
    private List<Button> buttons;

    public RecogCureFragment() {
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        apiManager = new ApiManager(getResources().getString(R.string.SERVER_URL));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recog_cure, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        initialize();

        refresh();
    }

    private void initialize(){
        View view = getView();
        imageView = view.findViewById(R.id.recog_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer != null){
                    tts.speak(answer);
                }
            }
        });
        buttons = new ArrayList<>();
        buttons.add(view.findViewById(R.id.recog_button1));
        buttons.add(view.findViewById(R.id.recog_button2));
        buttons.add(view.findViewById(R.id.recog_button3));
        buttons.add(view.findViewById(R.id.recog_button4));
    }

    private void refresh(){
        answer = null;
        for(Button b : buttons)
            b.setBackgroundColor(Color.WHITE);
        findStatementByImg();
    }

    private void findStatementByImg(){
        Call<Text_4_Img_1_Form> call = apiManager.getApiService().findStatementByImg(ldb.getAuthForm("token"));
        call.enqueue(new Callback<Text_4_Img_1_Form>() {
            @Override
            public void onResponse(Call<Text_4_Img_1_Form> call, Response<Text_4_Img_1_Form> response) {
                if(response.isSuccessful()){
                    answer = response.body().getTexts().get(0);
                    constructViews(response.body());
                }
                else{
                    Log.e("RecogCureFragment", "http fail code: "+response.code()+"\n"+
                        "request info: "+call.request());
                    noInternet();
                }
            }

            @Override
            public void onFailure(Call<Text_4_Img_1_Form> call, Throwable t) {
                Log.e("RecogCureFragment", "network error: "+t.getMessage()+"\n"+
                        "request info: "+call.request());
                noInternet();
            }
        });
    }

    private void constructViews(Text_4_Img_1_Form form){
        Bitmap bm = ImageLoader.convertToBitMap(form.getImgData());
        imageView.setImageBitmap(bm);

        List<Integer> mixList = Shuffler.get();
        for(int i=0; i<4; i++){
            String text = form.getTexts().get(mixList.get(i));
            buttons.get(i).setText(text);
            if(mixList.get(i) == 0){ // correct answer
                buttons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "정답입니다!", Toast.LENGTH_LONG).show();
                        refresh();
                    }
                });
            }
            else{ // wrong answer
                buttons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tts.speak(answer);
                        v.setBackgroundColor(Color.rgb(223, 100, 100));
                    }
                });
            }
        }
    }
    
    private void noInternet(){
        for(Button b : buttons){
            b.setText("서버 연결X");
        }
    }
}