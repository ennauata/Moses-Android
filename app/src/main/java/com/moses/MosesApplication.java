package com.moses;


import android.app.Application;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.moses.model.User;


public class MosesApplication extends Application {
    private AccessTokenTracker accessTokenTracker = null;
    private AccessToken accessToken = null;
    private CallbackManager callbackManager;
    private User user;
    private static MosesApplication singleton;

    public MosesApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        singleton = this;

        // Track access token
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                accessToken = currentAccessToken;
            }
        };
    }

    public AccessToken getFbAccessToken(){
        //Try to get an access token
        if(accessToken == null){
            this.accessToken = AccessToken.getCurrentAccessToken();
        }
        return this.accessToken;
    }

    public void setFbAccessToken(AccessToken accessToken){
        this.accessToken = accessToken;
    }

    public CallbackManager getCallbackManager(){
        return callbackManager;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }
}
