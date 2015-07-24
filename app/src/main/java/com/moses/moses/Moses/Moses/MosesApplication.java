package com.moses.moses.Moses.Moses;

import android.app.Application;

import org.json.JSONObject;

import java.util.List;


public class MosesApplication extends Application{
	private static List<JSONObject> selectedUsers;
	public static List<JSONObject> getSelectedUsers() {
	    return selectedUsers;
	}

	public void setSelectedUsers(List<JSONObject> users) {
	    selectedUsers = users;
	}
}
