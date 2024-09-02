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
import com.example.forest_app.form.AuthForm;
import com.example.forest_app.form.RegisterForm;
import com.example.forest_app.form.ResponseForm;
import com.example.forest_app.utils.ImageLoader;

import java.util.Locale;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private ApiManager apiManager = new ApiManager();
    private TextToSpeech tts;
    private ImageView imageView;
    private View fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.nav_host_fragment);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // BottomNavigationView와 NavController 연결
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(item -> {
            fragmentContainer.setVisibility(View.VISIBLE);
            return NavigationUI.onNavDestinationSelected(item, navController);
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
            int language = tts.setLanguage(Locale.KOREAN);
            if (language == TextToSpeech.LANG_MISSING_DATA
                    || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("MainActivity.onInit()", "Unsupported language");
            }
        } else {
            Log.e("MainActivity.onInit()", "Initialization failed");
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
