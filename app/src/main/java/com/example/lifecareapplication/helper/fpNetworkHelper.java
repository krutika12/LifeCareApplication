package com.example.lifecareapplication.helper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface fpNetworkHelper {


    @Headers({"Content-Type: application/json"})
    @GET("articles")
    Call<ArrayList<RecyclerData>> getarticles();

    @GET("api/user/getProducts")
    Call<QuestionsResponse> getProducts();


}
