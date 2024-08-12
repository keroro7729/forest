package com.example.forest_app.api;

import com.example.forest_app.form.ResponseForm;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/hello")
    Call<ResponseForm> hello();
}
