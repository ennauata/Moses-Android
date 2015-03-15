package com.moses.Moses;

import java.util.List;

import android.app.Application;

import com.facebook.model.GraphUser;

public class MosesApplication extends Application{
	private static List<GraphUser> selectedUsers;
	public static List<GraphUser> getSelectedUsers() {
	    return selectedUsers;
	}

	public void setSelectedUsers(List<GraphUser> users) {
	    selectedUsers = users;
	}
}
