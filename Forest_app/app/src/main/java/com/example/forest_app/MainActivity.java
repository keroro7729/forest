package com.example.forest_app;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private View fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentContainer = findViewById(R.id.nav_host_fragment);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        // NavHostFragment 가져오기
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Set up the Toolbar with NavController
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder().build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            // BottomNavigationView와 NavController 연결
            NavigationUI.setupWithNavController(navView, navController);

            navView.setOnNavigationItemSelectedListener(item -> {
                fragmentContainer.setVisibility(View.VISIBLE);
                return NavigationUI.onNavDestinationSelected(item, navController);
            });
        } else {
            Log.e("MainActivity", "NavHostFragment is null. Make sure it is correctly defined in the XML.");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    private void speakText(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = tts.setLanguage(Locale.KOREAN);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
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
