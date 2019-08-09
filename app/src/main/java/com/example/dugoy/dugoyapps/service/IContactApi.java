package com.example.dugoy.dugoyapps.service;

import com.example.dugoy.dugoyapps.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IContactApi {

    @GET("posts")
    Call<List<Post>> getPosts();



}
