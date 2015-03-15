package com.moses.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.facebook.model.GraphUser;
import com.moses.Utils.Callback;
import com.moses.Utils.WebServiceHandler;

public class UserDAO {
	private static UserDAO userInstance = null;
	private static WebServiceHandler userWebHandler;
	private static List<GraphUser> usersToAdd;
	private GraphUser userInfo;
	private JSONObject userServiceInfo;
	private String response;
	private Callback UserDaoCallback;

	public List<GraphUser> getUsersToAdd() {
		return usersToAdd;
	}

	public void setUsersToAdd(List<GraphUser> list) {
		UserDAO.usersToAdd = list;
	}

	public static WebServiceHandler getmWebHandler() {
		return userWebHandler;
	}

	public static void setmWebHandler(WebServiceHandler mWebHandler) {
		UserDAO.userWebHandler = mWebHandler;
	}

	public Callback getUserDaoCallback() {
		return UserDaoCallback;
	}

	public void setUserDaoCallback(Callback userDaoCallback) {
		UserDaoCallback = userDaoCallback;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public JSONObject getUserServiceInfo() {
		return userServiceInfo;
	}

	public void setUserServiceInfo(JSONObject userServiceInfo) {
		this.userServiceInfo = userServiceInfo;
	}

	public GraphUser getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(GraphUser userInfo) {
		this.userInfo = userInfo;
	}

	public static UserDAO getInstance() {
		if (userInstance == null) {
			userInstance = new UserDAO();
			userWebHandler = new WebServiceHandler();
			usersToAdd = new ArrayList<GraphUser>();
		}
		return userInstance;
	}

	public void checkUser(final String facebookId) throws IOException,
			InterruptedException {
		
		// Check if user exists
		userWebHandler.setGetRequestCallback(new Callback() {

			@Override
			public void callbackCall() {
				// Insert user if it does not exist yet
				if (response.equals("404")) {
					try {
						UserDAO.getInstance().insertUser();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					userWebHandler.getPostRequestCallback().callbackCall();
				}
			}
		});
		userWebHandler.sendGet("http://mosesapp.me/user/" + facebookId, "check");
	}

	public void insertUser() throws IOException {
		userWebHandler.sendPost("http://mosesapp.me/users/", "insertUser");
		// Setar informacoes do usuario no bin!!!!!
	}	
}
