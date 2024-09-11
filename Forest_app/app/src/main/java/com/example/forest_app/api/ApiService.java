package com.example.forest_app.api;

import com.example.forest_app.form.AuthForm;
import com.example.forest_app.form.RegisterForm;
import com.example.forest_app.form.ResponseForm;
import com.example.forest_app.form.Text_1_Img_4_Form;
import com.example.forest_app.form.Text_4_Form;
import com.example.forest_app.form.Text_4_Img_1_Form;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api/hello")
    Call<ResponseForm> hello();

    @GET("api/get-test-image")
    Call<ResponseForm> getImage();

    @POST("api/user/register-user")
    Call<AuthForm> registerUser(@Body RegisterForm registerForm);

    @POST("api/find-img-by-word")
    Call<Text_1_Img_4_Form> findImgByWord(@Body AuthForm auth);

    @POST("api/find-word-by-img")
    Call<Text_4_Img_1_Form> findWordByImg(@Body AuthForm auth);

    @POST("api/find-word-by-listening")
    Call<Text_4_Form> findWordByListening(@Body AuthForm auth);

    @POST("api/find-img-by-listening")
    Call<Text_1_Img_4_Form> findImgByListening(@Body AuthForm auth);

    @POST("api/try-speech")
    Call<ResponseForm> trySpeech(@Body AuthForm auth);

    @POST("api/find-statement-by-img")
    Call<Text_4_Img_1_Form> findStatementByImg(@Body AuthForm auth);
}
