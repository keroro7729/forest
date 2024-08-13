package com.example.forest_app;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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


public class MainActivity extends AppCompatActivity {

    private ApiManager apiManager = new ApiManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //navController BottomNavigationView
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        ImageView imageView = findViewById(R.id.imageView);
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



        EdgeToEdge.enable(this);

        Button helloTestButton = findViewById(R.id.button);
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
    }
}