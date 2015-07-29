package com.moses.rest.service;

import android.util.Base64;

import retrofit.RequestInterceptor;

public class SessionAuthInterceptor implements RequestInterceptor {
    private String userName;
    private String password;

    public SessionAuthInterceptor(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void intercept(RequestFacade request) {
        String credentials = userName + ":" + password;
        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        request.addHeader("Authorization", auth);
        request.addHeader("Accept", "application/json");
    }
}
