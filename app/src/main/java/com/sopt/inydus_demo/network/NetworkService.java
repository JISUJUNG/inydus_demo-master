package com.sopt.inydus_demo.network;

import com.sopt.inydus_demo.model.Authentication;
import com.sopt.inydus_demo.model.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface NetworkService {
    @GET("/session")
    Call<User> getSession();

    @POST("/session/sign/in")
    Call<User> login(@Body Authentication authentication);

    @POST("/user")
    Call<Boolean> duplicationTest(@Body String email);

    @POST("/user")
    Call<User> registerUser(@Body User user);
}
