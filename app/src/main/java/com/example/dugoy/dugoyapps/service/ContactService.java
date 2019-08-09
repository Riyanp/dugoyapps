package com.example.dugoy.dugoyapps.service;

import com.example.dugoy.dugoyapps.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactService {

    private static Retrofit retrofit;
    private static IContactApi jsonApi;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(IContactApi.class);
    }

    public static Call<List<Post>> getPosts() {
        return jsonApi.getPosts();
    }

}
