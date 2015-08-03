package com.moses.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.moses.MosesApplication;
import com.moses.R;
import com.moses.adapter.ImageAdapter;
import com.moses.model.User;
import com.moses.rest.MosesAPI;
import com.moses.rest.service.MosesApiService;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends FragmentActivity {
    private CallbackManager callbackManager;
    private MosesApiService mosesApi;
    private AccessToken accessToken = null;
    private MosesApplication mApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApplication = (MosesApplication) getApplication();


        //Setup facebook and moses APIs
        mosesApi = MosesAPI.getApiService();
        accessToken = mApplication.getFbAccessToken();
        callbackManager = mApplication.getCallbackManager();

        if (accessToken != null) {
            getUserFromFb();
        }

        // Setup page adapter
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(getApplicationContext());
        viewPager.setAdapter(adapter);
        final PageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);

        // Request Permissions
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = mApplication.getFbAccessToken();
                getUserFromFb();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("ERROR", exception.toString());
            }
        });

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getUserFromFb(){
        // Request facebook info
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            User user = new User();
                            user.setFacebookId(object.getString("id"));
                            user.setFirstName(object.getString("first_name"));
                            user.setFullName(object.getString("name"));
                            user.setEmail(object.getString("email"));
                            user.setLocale(object.getString("locale"));
                            user.setTimezone(object.getInt("timezone"));
                            setUpUser(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email, first_name, locale, timezone");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void setUpUser(final User user) {
        mosesApi.getUser(user.getFacebookId(), new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                if(users.size() == 0){
                    createUser(user);
                } else {
                    mApplication.setUser(users.get(0));
                    // Switch screen
                    startMainActivity();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.getBody();
            }
        });
    }

    private void createUser(User user) {
        // Create User
        mosesApi.createUser(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                mApplication.setUser(user);
                startMainActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                error.getBody();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
