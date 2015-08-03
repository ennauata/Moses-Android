package com.moses.rest.service;


import com.moses.model.Currency;
import com.moses.model.Group;
import com.moses.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface MosesApiService {
    @GET("/users/")
    public void listUsers(Callback<List<User>> callback);

    @GET("/users/{facebook_id}/")
    public void getUser(@Path("facebook_id") String facebookId, Callback<List<User>> callback);

    @POST("/users/")
    public void createUser(@Body User user, Callback<User> callback);

    @GET("/groups/")
    public void listGroups(Callback<List<Group>> callback);

    @POST("/groups/")
    public void createGroup(@Body Group group, Callback<Group> callback);

    @GET("/currencies/")
    public void listCurrencies(Callback<List<Currency>> callback);

    @POST("/currencies/")
    public void createUser(@Body Currency currency, Callback<Currency> callback);


}
