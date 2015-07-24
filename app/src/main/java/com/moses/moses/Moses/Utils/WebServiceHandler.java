package com.moses.moses.Moses.Utils;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.moses.moses.Moses.Bin.BillBin;
import com.moses.moses.Moses.DAO.BillsDAO;
import com.moses.moses.Moses.DAO.GroupDAO;
import com.moses.moses.Moses.DAO.UserDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WebServiceHandler {
	private String username = "admin";
	private String password = "Moses765";
	private String mod;
	private String groupName;
	private Integer userIdInsert;
	private String requestGroupFrom;
	private String fbIdtoAdd;
	private Callback getRequestCallback;
	private Callback postRequestCallback;
	private JSONObject UserToAddInfo;
	private BillBin bill;


	public BillBin getBill() {
		return bill;
	}

	public void setBill(BillBin bill) {
		this.bill = bill;
	}

	public JSONObject getUserToAddInfo() {
		return UserToAddInfo;
	}

	public void setUserToAddInfo(JSONObject userToAddInfo) {
		UserToAddInfo = userToAddInfo;
	}

	public Callback getGetRequestCallback() {
		return getRequestCallback;
	}

	public void setGetRequestCallback(Callback getRequestCallback) {
		this.getRequestCallback = getRequestCallback;
	}

	public Callback getPostRequestCallback() {
		return postRequestCallback;
	}

	public void setPostRequestCallback(Callback postRequestCallback) {
		this.postRequestCallback = postRequestCallback;
	}

	public String getFbIdtoAdd() {
		return fbIdtoAdd;
	}

	public void setFbIdtoAdd(String fbIdtoAdd) {
		this.fbIdtoAdd = fbIdtoAdd;
	}

	public WebServiceHandler() {
		super();
	}

	public Integer getUserIdInsert() {
		return userIdInsert;
	}

	public void setUserIdInsert(Integer userIdInsert) {
		this.userIdInsert = userIdInsert;
	}

	public Integer getGroupIdInsert() {
		return groupIdInsert;
	}

	public void setGroupIdInsert(Integer groupIdInsert) {
		this.groupIdInsert = groupIdInsert;
	}

	private Integer groupIdInsert;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void sendGet(final String url, String mod) throws IOException {
		new sendGetTask().execute(url);
		this.mod = mod;
	}

	private class sendGetTask extends AsyncTask<String, String, String> {
		private String response;

		@Override
		protected String doInBackground(String... urls) {
			String responseCode = null;
			try {
				URL obj = new URL(urls[0]);
				HttpURLConnection con = (HttpURLConnection) obj
						.openConnection();

				String userpass = username + ":" + password;
				String encoding = Base64.encodeToString(userpass.getBytes(),
						Base64.DEFAULT);

				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("Authorization", "Basic " + encoding);
				con.setRequestMethod("GET");

				responseCode = String.valueOf(con.getResponseCode());
				response = readStream(con).toString();
				if (responseCode != null) {
					Log.i("TESTE", responseCode);
				} else {
					Log.i("TESTE", "responseCode = NULL");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				return responseCode;
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// Log.i("TESTE", result);
			UserDAO.getInstance().setResponse(result);
			BillsDAO.getInstance().setResponse(result);
			GroupDAO.getInstance().setResponse(result);

			if (result != null) {
				Log.i("TESTE", result);
			} else {
				Log.i("TESTE", "Result = NULL");
			}

			if (getRequestCallback != null) {
				getRequestCallback.callbackCall();
			}
		}
	}

	public void sendPost(final String url, String mod) throws IOException {
		this.mod = mod;
		new sendPostTask().execute(url);
	}

	private class sendPostTask extends AsyncTask<String, String, String> {
		private String response;
		private String responseCode;

		@Override
		protected String doInBackground(String... urls) {
			try {
				URL obj = new URL(urls[0]);
				HttpURLConnection con = (HttpURLConnection) obj
						.openConnection();

				String userpass = username + ":" + password;
				String encoding = Base64.encodeToString(userpass.getBytes(),
						Base64.DEFAULT);

				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("Authorization", "Basic " + encoding);
				con.setRequestMethod("POST");
				con.setDoOutput(true);

				// Setup input JSON
				JSONObject jsonParam = null;
				if (mod.equals("insertUser")) {
					jsonParam = createUserJSON();
				} else if (mod.equals("createGroup")) {
					jsonParam = createGroupJSON();
				} else if (mod.equals("insertGroupUser")) {
					jsonParam = createInsertGroupUserJSON();
				} else if (mod.equals("createBill")) {
					jsonParam = createBill();
				}

				// Send POST output.
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						con.getOutputStream()));
				out.write(jsonParam.toString());
				out.close();
				responseCode = String.valueOf(con.getResponseCode());
				response = readStream(con).toString();
			} catch (IOException e) {
				return responseCode;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return response;
		}

		private JSONObject createBill() throws JSONException {
			JSONObject jsonParam = new JSONObject();

			String billName = bill.getName();
			String billDescription = bill.getDescription();
			String groupId = bill.getGroupId();
			float amount = bill.getAmount();
			String duedate =  bill.getDuedate();
			JSONArray members = new JSONArray();

			for (int i = 0; i < bill.getUserIds().length; i++) {
				float[] values = bill.getUservalues();
				String[] ids = bill.getUserIds();
				if (values[i] < 0) {
					JSONObject node = new JSONObject();
					node.put("member", ids[i]);
					node.put("relation", "debtor");

					members.put(node);
				} else {
					JSONObject node = new JSONObject();
					node.put("member", ids[i]);
					node.put("relation", "taker");
					node.put("amount", values[i]);

					members.put(node);
				}

			}

			jsonParam.put("name", billName);
			jsonParam.put("description", billDescription);
			jsonParam.put("group", groupId);
			jsonParam.put("receipt_image", JSONObject.NULL);
			jsonParam.put("amount", amount);
			jsonParam.put("deadline", duedate);
			jsonParam.put("members", members);

			Log.i("Proc", jsonParam.toString());
			return jsonParam;
		}

		private JSONObject createInsertGroupUserJSON() throws JSONException {
			JSONObject jsonParam = new JSONObject();

			Integer userId = userIdInsert;
			Integer groupId = groupIdInsert;

			jsonParam.put("user", userId);
			jsonParam.put("group", groupId);
			return jsonParam;
		}

		private JSONObject createGroupJSON() throws JSONException {
			JSONObject jsonParam = new JSONObject();
			JSONArray members = new JSONArray();
			//List<JSONObject> usersToAdd = MosesApplication.getSelectedUsers();
			
			//for (int i = 0; i < usersToAdd.size(); i++) {
			//	JSONObject node = new JSONObject();
			//	node.put("user_facebook", usersToAdd.get(i).getId());
			//	node.put("administrator", false);
			//	members.put(node);
			//}

			String name = groupName;
			Integer creator = UserDAO.getInstance().getUserServiceInfo()
					.getInt("id");
			jsonParam.put("name", name);
			jsonParam.put("image", JSONObject.NULL);
			jsonParam.put("creator", creator);
			jsonParam.put("members", members);
			
			return jsonParam;
		}

		private JSONObject createUserJSON() throws JSONException {
			//JSONObject jsonParam = new JSONObject();
			//String firstName = UserToAddInfo.getName();
			//String fullName = UserToAddInfo.getName();
			//String email = fullName;// (String)
									// UserToAddInfo.getProperty("email");
			//String facebookId = UserToAddInfo.getId();
			//String locale = "PT-BR";// (String)
									// UserToAddInfo.getProperty("locale");
			//Integer timezone = 1;// (Integer)
									// UserToAddInfo.getProperty("timezone");

			//jsonParam.put("first_name", firstName);
			//jsonParam.put("full_name", fullName);
			//jsonParam.put("email", email);
			//jsonParam.put("facebook_id", facebookId);
			//jsonParam.put("locale", locale);
			//jsonParam.put("timezone", timezone);

			//Log.i("Proc", jsonParam.toString());
			//return jsonParam;
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			BillsDAO.getInstance().setResponse(result);
			UserDAO.getInstance().setResponse(result);
			GroupDAO.getInstance().setResponse(result);
			if (postRequestCallback != null) {
				postRequestCallback.callbackCall();
			}
		}
	}

	public StringBuffer readStream(URLConnection con) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}

	public String getRequestGroupFrom() {
		return requestGroupFrom;
	}

	public void setRequestGroupFrom(String requestGroupFrom) {
		this.requestGroupFrom = requestGroupFrom;
	}

}
