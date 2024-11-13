package com.example.forest_app.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private Retrofit retrofit;
    private ApiService apiService;
    public ApiManager(String BASE_URL){
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

