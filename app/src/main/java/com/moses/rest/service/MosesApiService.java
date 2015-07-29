package com.moses.rest.service;


import com.moses.model.Group;
import com.moses.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface MosesApiService {
    @GET("/listUsers/")
    public void listUsers(Callback<List<User>> callback);

    @GET("/getUser/{facebook_id}/")
    public void getUser(@Path("facebook_id") String facebookId, Callback<List<User>> callback);

    @POST("/createUser/")
    public void createUser(@Body User user, Callback<User> callback);

    @GET("/listGroups")
    public void listGroups(Callback<List<Group>> callback);
}
