package com.example.user.myapplication.api;

import com.example.user.myapplication.cartoon.Cartoon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartoonResource {
    String BASE_URL = AppResource.BASE_URL;

    @POST("cartoons")
    Call<Cartoon> addCartoon(@Body Cartoon cartoon, @Header("Authorization") String token);

    @GET("cartoons")
    Call<List<Cartoon>> getCartoons();

    @GET("cartoons/cartoonsPaginated")
    Call<List<Cartoon>> getCartoonsPaginated(@Query("page_nr") Integer page_nr, @Query("size") Integer size, @Header("Authorization") String token);
}
