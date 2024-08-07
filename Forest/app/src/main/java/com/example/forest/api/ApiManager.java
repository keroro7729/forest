package com.example.forest.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final String BASE_URL = "http://192.168.50.59:8080/";
    private Retrofit retrofit;
    private ApiService apiService;
    public ApiManager(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Retrofit getRetrofit(){ return retrofit; }

    public ApiService getApiService(){ return apiService; }

    /*
    private void request(){
        Call<ResponseForm> call = apiManager.getApiService().service();
        call.enqueue(new Callback<ResponseForm>() {
            @Override
            public void onResponse(Call<ResponseForm> call, Response<ResponseForm> response) {
                if(response.isSuccessful()){
                    //
                }
                else{
                    String requestInfo = call.request().toString();
                    Log.e("", "Request Info: " + requestInfo);
                    Log.e("", "Http fail code: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseForm> call, Throwable t) {
                String requestInfo = call.request().toString();
                String errorMessage = t.getMessage();
                Log.e("", "Request Info: " + requestInfo);
                Log.e("", "Network Error: " + errorMessage);
            }
        });
    }*/
}
