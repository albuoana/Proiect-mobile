package com.example.user.myapplication.modelviews;

import com.example.user.myapplication.api.CartoonResource;
import com.example.user.myapplication.cartoon.Cartoon;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartoonModelView {
    public Call<List<Cartoon>> getCartoonsPaginated(Integer size, Integer page_nr, String token){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartoonResource.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartoonResource api = retrofit.create(CartoonResource.class);
        Call<List<Cartoon>> call = api.getCartoonsPaginated(page_nr, size, token);
        return call;
    }

    public Call<Cartoon> addCartoon(Cartoon cartoon, String token){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CartoonResource.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartoonResource api = retrofit.create(CartoonResource.class);
        Call<Cartoon> call = api.addCartoon(cartoon,token);
        return call;
    }
}
