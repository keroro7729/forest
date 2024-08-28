package com.example.forest_app.api;

import com.example.forest_app.form.AuthForm;
import com.example.forest_app.form.RegisterForm;
import com.example.forest_app.form.ResponseForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api/hello")
    Call<ResponseForm> hello();

    @GET("api/get-test-image")
    Call<ResponseForm> getImage();

    @POST("api/create-user")
    Call<AuthForm> createUser(@Body RegisterForm registerForm);
}
