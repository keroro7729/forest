package com.example.forest_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.ResponseForm;
import com.example.forest_app.utils.ImageLoader;

import java.util.Locale;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.ResponseForm;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private ApiManager apiManager = new ApiManager();
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button helloTestButton = findViewById(R.id.hello_test_button);

        helloTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseForm> call = apiManager.getApiService().hello();
                call.enqueue(new Callback<ResponseForm>() {
                    @Override
                    public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                        if(response.isSuccessful()){
                            Log.d("Hello", "Request Info: "+call.request().toString());
                            Log.d("Hello", "Received Message: "+response.body().getMessage());
                        }
                        else{
                            Log.e("Hello", "Request Info: "+call.request());
                            Log.e("Hello", "Fail code: "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseForm> call, Throwable throwable) {
                        Log.e("Hello", "Request Info: "+call.request());
                        Log.e("Hello", "Network Error: "+throwable.getMessage());
                    }
                });
            }
        });

        // image load testing
        ImageView imageView = findViewById(R.id.imageView);
        Bitmap bm;
        Call<ResponseForm> call = apiManager.getApiService().getImage();
        call.enqueue(new Callback<ResponseForm>() {
            @Override
            public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                if(response.isSuccessful()) {
                    Bitmap bm = ImageLoader.convertToBitMap(response.body().getData());
                    imageView.setImageBitmap(bm);
                }
                else{
                    Log.e("GetImage", "Request Info: "+call.request().toString());
                    Log.e("GetImage", "Received Message: "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseForm> call, Throwable t) {
                Log.e("GetImage", "Request Info: "+call.request().toString());
                Log.e("GetImage", "Network error: "+t.getMessage());
            }
        });

        // tts testing
        Button ttsButton = findViewById(R.id.tts_test_button);
        tts = new TextToSpeech(this, this);
        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakText("김지훈 바보");
                Log.d("김지훈 바보", "김지훈 바보");
            }
        });


        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        View fragmentContainer = findViewById(R.id.nav_host_fragment);

        // BottomNavigationView와 NavController 연결
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // ImageView를 숨기고 FragmentContainerView를 보여줌
                imageView.setVisibility(View.GONE);
                fragmentContainer.setVisibility(View.VISIBLE);
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });
    }

    private void speakText(String text) {
        if(tts != null){
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = tts.setLanguage(Locale.KOREAN);  // 언어 설정
            if (language == TextToSpeech.LANG_MISSING_DATA
                    || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("MainActivity.onInit()", "unsported language");
            }
        } else {
            Log.e("MainActivity.onInit()", "initialization failed");
        }
    }

    @Override

    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}