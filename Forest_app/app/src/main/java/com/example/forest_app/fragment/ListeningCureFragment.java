package com.example.forest_app.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.Text_1_Img_4_Form;
import com.example.forest_app.form.Text_4_Form;
import com.example.forest_app.form.Text_4_Img_1_Form;
import com.example.forest_app.utils.ImageLoader;
import com.example.forest_app.utils.LocalDatabase;
import com.example.forest_app.utils.Shuffler;
import com.example.forest_app.utils.TTSManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeningCureFragment extends Fragment {

    private final ApiManager apiManager = new ApiManager();
    private LocalDatabase ldb;
    private final TTSManager tts = TTSManager.getInstance(getContext());
    private Button lcbutton1, lcbutton2, lcbutton3, lcbutton4;
    private ImageView speakImageView;
    private String answer;

    public ListeningCureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listening_cure, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize();

        refresh();
    }

    private void initialize(){
        ldb = LocalDatabase.getInstance(getContext());
        View view = getView();
        lcbutton1 = view.findViewById(R.id.lc_button1);
        lcbutton2 = view.findViewById(R.id.lc_button2);
        lcbutton3 = view.findViewById(R.id.lc_button3);
        lcbutton4 = view.findViewById(R.id.lc_button4);
        speakImageView = view.findViewById(R.id.speak_imageview);
        speakImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer != null)
                    tts.speak(answer);
            }
        });
    }

    private void refresh(){
        answer = null;
        lcbutton1.setBackgroundColor(Color.rgb(226, 226, 226));
        lcbutton2.setBackgroundColor(Color.rgb(226, 226, 226));
        lcbutton3.setBackgroundColor(Color.rgb(226, 226, 226));
        lcbutton4.setBackgroundColor(Color.rgb(226, 226, 226));
        findWordByListening();
    }

    private void findWordByListening(){
        Call<Text_4_Form> call = apiManager.getApiService().findWordByListening(ldb.getAuthForm("token"));
        call.enqueue(new Callback<Text_4_Form>() {
            @Override
            public void onResponse(Call<Text_4_Form> call, Response<Text_4_Form> response) {
                if(response.isSuccessful()){
                    Text_4_Form form = response.body();
                    answer = form.getTexts().get(0);
                    constructButtons(form);
                }
                else{
                    Log.e("ListeningCure", "http error code: "+response.code()+"\n"
                        +"request info: "+call.request());
                    noInternet();
                }
            }

            @Override
            public void onFailure(Call<Text_4_Form> call, Throwable t) {
                Log.e("ListeningCure", "Network error: "+t.getMessage()+"\n"
                        +"request info: "+call.request());
                noInternet();
            }
        });
    }

    private void constructButtons(Text_4_Form form){
        int count = 0;
        //{1, 3 ,0, 2}
        for(int i : Shuffler.get()){
            String text = form.getTexts().get(i);
            switch(count++){
                case 0: lcbutton1.setText(text); setClickListener(lcbutton1, i == 0); break;
                case 1: lcbutton2.setText(text); setClickListener(lcbutton2, i == 0); break;
                case 2: lcbutton3.setText(text); setClickListener(lcbutton3, i == 0); break;
                case 3: lcbutton4.setText(text); setClickListener(lcbutton4, i == 0); break;
                default: // error log
            }
        }
        tts.speak(answer+"를 선택하세요");
    }

    private void setClickListener(Button button, boolean isAnswer){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnswer){
                    tts.speak("정답입니다");
                    refresh();
                }
                else{
                    tts.speak("틀렸습니다. "+answer+"를 다시 선택해보세요.");
                    button.setBackgroundColor(Color.rgb(223, 100, 100));
                }
            }
        });
    }

    private void noInternet(){
        lcbutton1.setText("서버연결X");
        lcbutton2.setText("서버연결X");
        lcbutton3.setText("서버연결X");
        lcbutton4.setText("서버연결X");
    }
}