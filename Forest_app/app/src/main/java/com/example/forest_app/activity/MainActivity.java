package com.example.forest_app.activity;

import android.content.Intent;
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

import com.example.forest_app.R;
import com.example.forest_app.form.AuthForm;
import com.example.forest_app.utils.LocalDatabase;
import com.example.forest_app.utils.TTSManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TTSManager tts;
    private View fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init local database
        LocalDatabase ldb = LocalDatabase.getInstance(this);
        ldb.remove("token");

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

        // initialize tts manager
        tts = TTSManager.getInstance(this);

        // start register activity
        if(ldb.getAuthForm("token") == null) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        tts.shutdown();
        super.onDestroy();
    }
}
