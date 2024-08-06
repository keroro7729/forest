package com.example.forest.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/hello")
    Call<ResponseForm> hello();
}
