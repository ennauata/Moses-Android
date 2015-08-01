package com.moses.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moses.rest.service.MosesApiService;
import com.moses.rest.service.MosesJsonAdapterFactory;
import com.moses.rest.service.SessionAuthInterceptor;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class MosesAPI {
    private static final String BASE_URL = "http://192.168.59.103:8000";
    private static MosesApiService mosesApiService;
    private String userName = "admin";
    private String password = "Moses765";

    private MosesAPI() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new MosesJsonAdapterFactory())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(this.BASE_URL)
                .setRequestInterceptor(new SessionAuthInterceptor(userName, password))
                .setConverter(new GsonConverter(gson))
                .build();

        this.mosesApiService = restAdapter.create(MosesApiService.class);
    }

    public static MosesApiService getApiService() {
        if (mosesApiService == null) {
            new MosesAPI();
        }
        return mosesApiService;
    }
}
