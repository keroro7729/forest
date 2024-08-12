package com.example.forest_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.ResponseForm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiManager apiManager = new ApiManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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