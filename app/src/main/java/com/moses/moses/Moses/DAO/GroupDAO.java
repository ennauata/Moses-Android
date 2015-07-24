package com.moses.moses.Moses.DAO;

import android.util.Log;

import com.moses.moses.Moses.Bin.GroupBin;
import com.moses.moses.Moses.Utils.Callback;
import com.moses.moses.Moses.Utils.WebServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GroupDAO {
	private String response;
	static ArrayList<GroupBin> groups;
	private static GroupDAO groupInstance;
	private static WebServiceHandler groupWebHandler;
	private Callback GroupDaoCallback;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public static ArrayList<GroupBin> getGroups() {
		return groups;
	}

	public static void setGroups(ArrayList<GroupBin> groups) {
		GroupDAO.groups = groups;
	}

	public static GroupDAO getGroupInstance() {
		return groupInstance;
	}

	public static void setGroupInstance(GroupDAO groupInstance) {
		GroupDAO.groupInstance = groupInstance;
	}

	public static WebServiceHandler getGroupWebHandler() {
		return groupWebHandler;
	}

	public static void setGroupWebHandler(WebServiceHandler groupWebHandler) {
		GroupDAO.groupWebHandler = groupWebHandler;
	}

	public Callback getGroupDaoCallback() {
		return GroupDaoCallback;
	}

	public void setGroupDaoCallback(Callback groupDaoCallback) {
		GroupDaoCallback = groupDaoCallback;
	}

	public static GroupDAO getInstance() {
		if (groupInstance == null) {
			groupInstance = new GroupDAO();
			groups = new ArrayList<GroupBin>();
			groupWebHandler = new WebServiceHandler();
		}
		return groupInstance;
	}

	public void requestGroups(final String userId) throws IOException,
			JSONException, InterruptedException {
		GroupDAO.getGroupWebHandler().setGetRequestCallback(new Callback() {

			@Override
			public void callbackCall() {
				// TODO Auto-generated method stub

				try {
					// Set groups
					groups = new ArrayList<GroupBin>();
					JSONObject jResponse = new JSONObject(response);
					JSONArray results = new JSONArray();
					results = jResponse.getJSONArray("results");
					
					for (int i = 0; i < results.length(); i++) {

						JSONObject jElement = new JSONObject();
						jElement = (JSONObject) results.get(i);
						
						JSONObject jGroup = new JSONObject();
						jGroup = (JSONObject) jElement.getJSONObject("group");
 
						int id = jGroup.getInt("id");
						String name = jGroup.getString("name");
						String image = jGroup.getString("image");
						int creator = jGroup.getInt("creator");
						String status = jGroup.getString("status");

						GroupBin group = new GroupBin(name, id, image, creator,
								status);

						groups.add(group);
					}
					GroupDAO.setGroups(groups);
					
					// Call callback
					GroupDAO.getInstance().getGroupDaoCallback()
							.callbackCall();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		// Request list of groups
		groupWebHandler.sendGet("http://mosesapp.me/group_user_user/" + userId,
				"requestGroup");
	}

	public void createGroup(String groupName) throws IOException {
		groupWebHandler.setGroupName(groupName);
		groupWebHandler.setPostRequestCallback(new Callback() {

			@Override
			public void callbackCall() {
				// TODO Auto-generated method stub
				try {
					Log.i("Proc", response.toString());
					requestGroups(UserDAO.getInstance().getUserServiceInfo()
							.getString("id"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GroupDAO.getInstance().getGroupDaoCallback().callbackCall();
			}
		});
		Log.i("Proc", "Create Group");
		groupWebHandler.sendPost("http://mosesapp.me/group/", "createGroup");
	}

	public void requestUserInGroup(String groupId) throws IOException {
		GroupDAO.getGroupWebHandler().setGetRequestCallback(new Callback() {

			@Override
			public void callbackCall() {
				GroupDAO.getInstance().getGroupDaoCallback().callbackCall();
			}
		});
		groupWebHandler.sendGet("http://mosesapp.me/group_user_group/"
				+ groupId, "requestUserInGroup");
	}
}
